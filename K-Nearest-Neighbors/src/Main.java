
import java.io.FileNotFoundException;
import java.util.*;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        Scanner scanner = new Scanner(System.in);
        boolean keepWorking;
        System.out.print("Insert k -> ");
        int k = Integer.parseInt(scanner.nextLine());

        List<MyVector> myTrainVectors = MyOperations.readVectors("train-set.csv");
        MyOperations trainOperations = new MyOperations(myTrainVectors);

        List<MyVector> myTestVectors = MyOperations.readVectors("test-set.csv");
        MyOperations testOperations = new MyOperations(myTestVectors);

        trainOperations.operate(k);

        System.out.println("Train-set accuracy -> " + trainOperations.getAccuracy());

        testOperations.operate(k);

        System.out.println("Test-set accuracy -> " + testOperations.getAccuracy());

        do {
            System.out.print("Insert your vector -> ");
            String s = scanner.nextLine();
            List<Double> values = new ArrayList<>();
            String[] split = s.split(";");
            for (String value : split) {
                values.add(Double.parseDouble(value));
            }
            trainOperations.insertByHuman(new MyVector(values, ""));

            System.out.print("Keep working? [Y/N] -> ");

            String decision = scanner.nextLine();

            keepWorking = decision.equals("Y");

        } while (keepWorking);
    }
}
