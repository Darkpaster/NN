package dark.paster;

import java.util.ArrayList;

public class Neuron {
private double neuronValue;
private double weight;
private double biases;

public Neuron(){
  neuronValue = 0;
}

public Neuron(double value){
  this.neuronValue = value;
}

public void print(){
  System.out.println("NeuronValue: " + neuronValue);
  System.out.println("weight: " + weight);
  System.out.println("biases: " + biases);
}
public double getNeuronValue(){
  return neuronValue;
}
  public double getWeight(){
    return weight;
  }
  public double getBiases(){
    return biases;
  }

  public void setNeuronValue(double d){
    neuronValue = d;
  }
  public void addToNeuronValue(double d){
    neuronValue += d;
  }
  public void addToBiases(double d){
    biases += d;
  }

  public void mulToNeuronValue(double d){
    neuronValue *= d;
  }
  public void setWeight(double d){
    weight = d;
  }
  public void mulWeight(double d){
    weight *= d;
  }
  public void addToWeight(double d){
    weight += d;
  }
  public void setBiases(double d){
    biases = d;
  }
}
