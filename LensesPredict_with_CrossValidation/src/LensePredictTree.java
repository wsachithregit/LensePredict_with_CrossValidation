
import java.io.FileReader;
import java.util.Random;

import weka.classifiers.Evaluation;
import weka.classifiers.evaluation.ThresholdCurve;
import weka.classifiers.trees.J48;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

public class LensePredictTree {

	public static void main(String[] args) throws Exception {
		FileReader reader = new FileReader("C:\\Users\\Sachithre\\eclipse-workspace\\lense.arff"); // read the dataset file
		Instances instance_train = new Instances(reader); //create an instance
		instance_train.setClassIndex(4);

		J48 j48 = new J48();
		Evaluation evl = new Evaluation(instance_train);
		evl.crossValidateModel(j48, instance_train, 10, new Random());
		
		ThresholdCurve tc = new ThresholdCurve();
		Instances Tdata = tc.getCurve(evl.predictions());
		double auc = ThresholdCurve.getROCArea(Tdata);
		System.out.println("AUC = " + auc);
		
		j48.buildClassifier(instance_train);
		System.out.println("Decision Tree model");
		System.out.println(j48.toString());
		
		Instance inst = new DenseInstance(4);
		inst.setDataset(instance_train);
		
		inst.setValue(0, "pre-presbyopic"); 
		inst.setValue(1, "myope"); 
		inst.setValue(2, "yes"); 
		inst.setValue(3, "normal"); 
		
		System.out.println("The instance: " + inst); 
		System.out.println(j48.classifyInstance(inst));		
		
	}

}
