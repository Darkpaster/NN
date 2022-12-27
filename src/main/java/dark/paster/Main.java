package dark.paster;

import java.util.ArrayList;
import java.util.function.UnaryOperator;

public class Main {
    static NeuralNetwork neuralNetwork;
    public static void main(String[] args) { // not bear - 1 && bear - 0 doesn't work
                                            // not bear - 0 && bear - 1 work
        UnaryOperator<Double> sigmoid = x -> 1 / (1 + Math.exp(-x));
        UnaryOperator<Double> dsigmoid = y -> y * (1 - y);
        neuralNetwork = new NeuralNetwork(0.01, sigmoid, dsigmoid, 10000, 1024, 512, 92, 2);
        neuralNetwork.learn(100);
//        System.exit(0);
//        neuralNetwork.printOutput();
//        ArrayList<Neuron> test = new ArrayList<>();
//        for (int i = 0; i < 10000; i++) {
//            test.add(new Neuron(Math.random() * 255));
//        }
//        neuralNetwork.feedForward(test);
//        System.out.println("After feed:");
//        neuralNetwork.printOutput();
//        test.clear();
//        for (int i = 0; i < 10000; i++) {
//            test.add(new Neuron(Math.random() * 255));
//        }
//        System.out.println("one more time");
//        neuralNetwork.printOutput();
//        System.out.println("after back:");
//        ArrayList<Neuron> targets = new ArrayList<>();
//        targets.add(new Neuron(0));
//        //targets.add(new Neuron(1));
//        neuralNetwork.backPropagation(targets);
//        neuralNetwork.feedForward(test);
//        neuralNetwork.printOutput();
//        System.out.println("one more time");
//        neuralNetwork.backPropagation(targets);
//        neuralNetwork.feedForward(test);
//        neuralNetwork.printOutput();
//        System.out.println("one more time");
//        neuralNetwork.backPropagation(targets);
//        neuralNetwork.feedForward(test);
//        neuralNetwork.printOutput();
//        System.out.println("one more time");
    }

    public static int getRandomByte(){
        return Math.random() > 0.5 ? 1: 0;
    }
}