package dark.paster;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.AreaAveragingScaleFilter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.UnaryOperator;

public class NeuralNetwork {
private double learningRate;
private UnaryOperator<Double> activation;
private UnaryOperator<Double> derivative;
private ArrayList<Layer> layers;
public NeuralNetwork(double learningRate, UnaryOperator<Double> activation, UnaryOperator<Double> derivative, int... neurons){
  this.learningRate = learningRate;
  this.layers = new ArrayList<>(neurons.length);
  this.activation = activation;
  this.derivative = derivative;
  int j = 0;
  for(int i: neurons){
    layers.add(new Layer(i));
    for(Neuron n: layers.get(j).getListOfNeurons()){
      n.setWeight(Math.random() * 2.0 - 1.0);
      n.setBiases(Math.random() * 2.0 - 1.0);
    }
    j++;
  }
  layers.get(0).setName("Input");
  layers.get(layers.size() - 1).setName("Output");
}
public ArrayList<Neuron> feedForward(ArrayList<Neuron> input){
  layers.get(0).setListOfNeurons(input);
  for(int i = 1; i < layers.size(); i++){
    Layer prevLayer = layers.get(i - 1);
    for (int j = 0; j < layers.get(i).getNumberOfNeuronsInLayer(); j++) {
      layers.get(i).getNeuron(j).setNeuronValue(0);
      for (int k = 0; k < prevLayer.getNumberOfNeuronsInLayer(); k++) {
        layers.get(i).getNeuron(j).addToNeuronValue(prevLayer.getNeuron(k).getNeuronValue() * prevLayer.getNeuron(k).getWeight());
      }
      layers.get(i).getNeuron(j).addToNeuronValue(layers.get(i).getNeuron(j).getBiases());
      layers.get(i).getNeuron(j).setNeuronValue(activation.apply(layers.get(i).getNeuron(j).getNeuronValue()));
    }
  }
  return layers.get(layers.size() - 1).getListOfNeurons();
}

public void print(){
for(Layer layer: layers){
  System.out.println(layer.getName());
  for(Neuron n: layer.getListOfNeurons()){
    System.out.println(n);
    n.print();
  }
}
}

public void printOutput(){
  int i = 0;
  for(Neuron n: layers.get(layers.size() - 1).getListOfNeurons()){
    System.out.println("output" + i + ": " + n.getNeuronValue());
    i++;
  }
}

public void backPropagation(ArrayList<Neuron> targets){
ArrayList<Double> errors = new ArrayList<>();
  for (int i = 0; i < layers.get(layers.size() - 1).getNumberOfNeuronsInLayer(); i++) {
    errors.add(targets.get(i).getNeuronValue() - layers.get(layers.size() - 1).getNeuron(i).getNeuronValue());
  }
  for (int i = layers.size() - 2; i >= 0; i--) {
    Layer nextLayer = layers.get(i + 1);
    ArrayList<Double> gradients = new ArrayList<>();
    ArrayList<Double> nextErrors = new ArrayList<>();
    ArrayList<Double> deltas = new ArrayList<>();
    for (int j = 0; j < nextLayer.getNumberOfNeuronsInLayer(); j++) {
      gradients.add((errors.get(j) * derivative.apply(nextLayer.getNeuron(j).getNeuronValue())) * learningRate);
      for (int k = 0; k < layers.get(i).getNumberOfNeuronsInLayer(); k++) {
        deltas.add(gradients.get(j) * layers.get(i).getNeuron(k).getNeuronValue());
      }
    }
    //int test = (int) (Math.random() * layers.get(i).getNumberOfNeuronsInLayer());
    //System.out.println(deltas.get(test));
    for (int j = 0; j < layers.get(i).getNumberOfNeuronsInLayer(); j++) {
      double sum = 0;
      for (int k = 0; k < nextLayer.getNumberOfNeuronsInLayer() ; k++) {
        sum += layers.get(i).getNeuron(j).getWeight() * errors.get(k);
      }
      nextErrors.add(sum);
    }
    errors.clear();
    errors = nextErrors;
    //System.out.println(nextErrors.size());
    double[][] newWeights = new double[layers.get(i).getNumberOfNeuronsInLayer()][nextLayer.getNumberOfNeuronsInLayer()];
    for (int j = 0; j < nextLayer.getNumberOfNeuronsInLayer(); j++) {
      for (int k = 0; k < layers.get(i).getNumberOfNeuronsInLayer(); k++) {
        newWeights[k][j] = (layers.get(i).getNeuron(k).getWeight() + deltas.get(k));
        layers.get(i).getNeuron(k).setWeight(newWeights[k][j]);
      }
    }
    for (int j = 0; j < nextLayer.getNumberOfNeuronsInLayer(); j++) {
      layers.get(i + 1).getNeuron(j).addToBiases(gradients.get(j));
    }
  }
}

public void learn(int cycle){
int samples = 276;
  BufferedImage[] images = new BufferedImage[samples];
  boolean[] bears = new boolean[samples];
  File[] imagesFiles = new File("animals").listFiles();
  for (int i = 0; i < samples; i++) {
    try {
      images[i] = ImageIO.read(imagesFiles[i]);
    } catch (IOException e) {
      e.printStackTrace();
    }
    bears[i] = imagesFiles[i].getName().startsWith("bear");
  }
  ArrayList<ArrayList<Neuron>> inputs = new ArrayList<>();
  for (int i = 0; i < samples; i++) {
    ArrayList<Neuron> sample = new ArrayList<>();
    for (int y = 0; y < images[i].getHeight(); y += images[i].getHeight() / 10) {
      for (int x = 0; x < images[i].getWidth(); x += images[i].getWidth() / 10) {
        sample.add(new Neuron((images[i].getRGB(x, y) & 0xff) / 255.0));
      }
    }
    inputs.add(sample);
  }
  for (int i = 0; i < cycle; i++) {
    int right = 0;
    double errorSum = 0;
    int batchSize = 100;
    double mOut0 = 0;
    double mOut1 = 0;
    for (int j = 0; j < batchSize; j++) {
      mOut0 = 0;
      mOut1 = 0;
      int imgIndex = (int)(Math.random() * samples);
      int[] target = new int[2];
      boolean z = bears[imgIndex];
      int bear = z ? 1 : 0;
      target[0] = 1;
      ArrayList<Neuron> input = inputs.get(imgIndex);
      ArrayList<Neuron> outputs = feedForward(input);
      mOut0 += outputs.get(0).getNeuronValue();
      mOut1 += outputs.get(1).getNeuronValue();
      //System.out.println("output(1): "+outputs.get(1).getNeuronValue());
      //System.out.println("output(0): "+outputs.get(0).getNeuronValue());
      int selected = 0;
      double maxOutWeight = -1;
      for (int k = 0; k < 2; k++) {
        //System.out.println("output("+k+"): "+outputs.get(k).getNeuronValue());
        if(outputs.get(k).getNeuronValue() > maxOutWeight) {
          maxOutWeight = outputs.get(k).getNeuronValue();
          selected = k;
        }
      }
      if(0 == selected) right++;
      for (int k = 0; k < 2; k++) {
        errorSum += (target[k] - outputs.get(k).getNeuronValue()) * (target[k] - outputs.get(k).getNeuronValue());
      }
      ArrayList<Neuron> listTargets = new ArrayList<>();
      for(int b: target){
        listTargets.add(new Neuron(b));
      }
      //System.out.println((bear ? 1 : 0) + "==" + selected + ":  " + ((bear ? 1 : 0) == selected));
      //System.out.println("not bear: "+listTargets.get(0).getNeuronValue());
      //System.out.println("bear: "+listTargets.get(1).getNeuronValue());
      //System.out.println("target(1): " + listTargets.get(1).getNeuronValue());
      //System.out.println("target(0): " + listTargets.get(0).getNeuronValue());
      backPropagation(listTargets);
      //System.out.println("end of batch (" + j + ")");
    }
    System.out.println("cycle: " + i + ". correct: " + right + "%. error: " + errorSum + ". output(0): " + mOut0 * 0.01 + ". output(1): " + mOut1 * 0.01);
  }
}

public int getNumberOfLayers(){
  return layers.size();
}
public Layer getLayer(int i){
  return layers.get(i);
}
}
