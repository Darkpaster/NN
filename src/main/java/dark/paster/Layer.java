package dark.paster;

import java.util.ArrayList;
public class Layer {
private ArrayList<Neuron> listOfNeurons;
private int numberOfNeuronsInLayer;

private String name;

public Layer(int numberOfNeurons){
    ArrayList<Neuron> neurons = new ArrayList<>();
    for(int i = 0; i < numberOfNeurons; i++){
        neurons.add(new Neuron());
    }
    setListOfNeurons(neurons);
    setNumberOfNeuronsInLayer(neurons.size());
    name = "Hidden";
}

public void setListOfNeurons(ArrayList<Neuron> listOfNeurons){
    this.listOfNeurons = listOfNeurons;
}

public ArrayList<Neuron> getListOfNeurons(){
    return this.listOfNeurons;
}

public Neuron getNeuron(int i){
    return this.getListOfNeurons().get(i);
}
public String getName(){
    return this.name;
}
public void setName(String name){
    this.name = name;
}

public int getIndexOf(Neuron n){return this.getListOfNeurons().indexOf(n);}


    public void setNumberOfNeuronsInLayer(int numberOfNeuronsInLayer){
        this.numberOfNeuronsInLayer = numberOfNeuronsInLayer;
    }

    public int getNumberOfNeuronsInLayer(){
    numberOfNeuronsInLayer = listOfNeurons.size();
        return this.numberOfNeuronsInLayer;
    }

}
