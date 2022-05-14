import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;

public class MyOperations {

    private final List<MyVector> listOfVectors;
    private int k;
    private double accuracy = 0.0;

    public MyOperations(List<MyVector> listOfVectors) {
        this.listOfVectors = listOfVectors;
    }

    public static List<MyVector> readVectors(String filePath) throws FileNotFoundException {

        /* reading vectors from file */

        BufferedReader br = new BufferedReader(new FileReader(filePath));

        return br.lines()
                .map(l -> {
                    String[] split = l.split(";");
                    List<Double> values = new ArrayList<>();
                    for (int i = 0; i < split.length - 1; i++) {
                        values.add(Double.parseDouble(split[i]));
                    }
                    return new MyVector(values, split[split.length-1]);
                }).collect(Collectors.toList());
    }

    private List<MyVector> getKNearest(List<MyVector> vectors, MyVector myVector, int k){

        /* in this method I calculate Euklides distance from 'myVector' to every element in 'vectors'  */

        return vectors.stream()
                .map(v -> Map.entry(v, countEuklidesDistance(myVector, v)))
                .sorted(Map.Entry.comparingByValue())
                .limit(k)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

    }

    private Double countEuklidesDistance(MyVector mainVector, MyVector secondVector){

        /* Euklides distance pattern */

        double sum = 0.0;
        for (int i = 0; i < mainVector.getValues().size(); i++) {
            sum += Math.pow(mainVector.getValues().get(i) - secondVector.getValues().get(i), 2);
        }
        return Math.sqrt(sum);
    }

    private String findMostCommon(List<MyVector> vectorList){

        /* in K-Nearest Vectors I needed to find most frequently existed flower name */

        List<String> distinctFlowers = vectorList.stream()
                .distinct()
                .map(MyVector::getFlowerType)
                .collect(Collectors.toList());

        Map<String, Integer> cardinalityMap = new LinkedHashMap<>();

        for (String distinctFlower : distinctFlowers) {
            int counter = 0;
            for (MyVector myVector : vectorList) {
                if (distinctFlower.equals(myVector.getFlowerType()))
                    counter++;
            }
            cardinalityMap.put(distinctFlower, counter);
        }

        Optional<Map.Entry<String, Integer>> max = cardinalityMap.entrySet().stream()
                .max(Map.Entry.comparingByValue());

        return max
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    public void operate(int k){

        this.k = k;

        /* getKNearest() and findMostCommon() iterating for every element in source (listOfVectors) */

        double accuracyCounter = 0.0;

        for (MyVector singleVector : listOfVectors) {
            List<MyVector> tempList = new ArrayList<>(listOfVectors);
            tempList.remove(singleVector);
            List<MyVector> kNearest = getKNearest(tempList, singleVector, k);
            String mostCommon = findMostCommon(kNearest);
            if (mostCommon.equals(singleVector.getFlowerType())) {
                accuracyCounter++;
            }
        }

        this.accuracy = accuracyCounter / listOfVectors.size();

    }

    public void insertByHuman(MyVector vector){

        /* Option that allows user to calculate his own flower, just need to put vector on console */

        List<MyVector> kNearest = getKNearest(listOfVectors, vector, k);
        String mostCommon = findMostCommon(kNearest);
        System.out.println(mostCommon);
    }

    public double getAccuracy() {
        return accuracy;
    }
}