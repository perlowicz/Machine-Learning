import java.util.List;
import java.util.Objects;

public class MyVector {

    private List<Double> values;
    private String flowerType;

    public MyVector(List<Double> values, String flowerType) {
        this.values = values;
        this.flowerType = flowerType;
    }

    public List<Double> getValues() {
        return values;
    }

    public void setValues(List<Double> values) {
        this.values = values;
    }

    public String getFlowerType() {
        return flowerType;
    }

    public void setFlowerType(String flowerType) {
        this.flowerType = flowerType;
    }

    @Override
    public String toString() {
        return values.stream().map(d -> d + ";") + flowerType;
    }


    /*  equals() and hashCode() override so I could use 'remove()' method in MyOperations class, operate() method.
        Problem was that Java compared 2 'MyVector' objects by their localization in memory instead of object attributes
        values without those implementations */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyVector myVector = (MyVector) o;
        return values.equals(myVector.values) && flowerType.equals(myVector.flowerType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(values, flowerType);
    }
}
