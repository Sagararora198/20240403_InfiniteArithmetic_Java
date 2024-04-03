import java.util.ArrayList;
import java.util.List;

public class InfiniteNumber {
    private List<Integer> internalArray;

    // Constructor simplified to accept List<Integer> only
    public InfiniteNumber(List<Integer> internalArray) {
        this.internalArray = new ArrayList<>(internalArray);
    }

    public List<Integer> getInternalArray() {
        return new ArrayList<>(internalArray);
    }

    public String getNumberAsString() {
        StringBuilder sb = new StringBuilder();
        for (Integer digit : internalArray) {
            sb.append(digit);
        }
        return sb.toString();
    }

    // Example addition method
    public InfiniteNumber add(InfiniteNumber other) {
        List<Integer> result = new ArrayList<>();
        List<Integer> num1 = this.getInternalArray();
        List<Integer> num2 = other.getInternalArray();

        int carry = 0;
        int i = num1.size() - 1, j = num2.size() - 1;
        while (i >= 0 || j >= 0 || carry != 0) {
            int sum = carry;
            if (i >= 0) sum += num1.get(i--);
            if (j >= 0) sum += num2.get(j--);

            result.add(0, sum % 10);
            carry = sum / 10;
        }

        return new InfiniteNumber(result);
    }
    public InfiniteNumber subtract(InfiniteNumber other) {
        List<Integer> result = new ArrayList<>();
        List<Integer> num1 = this.getInternalArray();
        List<Integer> num2 = other.getInternalArray();

        int borrow = 0;
        int i = num1.size() - 1, j = num2.size() - 1;
        while (i >= 0 || j >= 0) {
            int diff = borrow;
            if (i >= 0) diff += num1.get(i--);
            if (j >= 0) diff -= num2.get(j--);

            if (diff < 0) {
                diff += 10;
                borrow = -1;
            } else {
                borrow = 0;
            }

            result.add(0, diff);
        }

        // Remove leading zeros
        while (result.size() > 1 && result.get(0) == 0) {
            result.remove(0);
        }

        return new InfiniteNumber(result);
    }
    public int compare(InfiniteNumber other) {
        List<Integer> num1 = this.getInternalArray();
        List<Integer> num2 = other.getInternalArray();

        // First, compare lengths
        if (num1.size() < num2.size()) {
            return -1; // This InfiniteNumber is smaller
        } else if (num1.size() > num2.size()) {
            return 1; // This InfiniteNumber is larger
        }

        // If lengths are equal, compare digit by digit from the most significant digit
        for (int i = 0; i < num1.size(); i++) {
            if (num1.get(i) < num2.get(i)) {
                return -1; // This InfiniteNumber is smaller
            } else if (num1.get(i) > num2.get(i)) {
                return 1; // This InfiniteNumber is larger
            }
        }

        // If we reach this point, the numbers are equal
        return 0;
    }
    public InfiniteNumber multiply(InfiniteNumber other) {
        InfiniteNumber result = new InfiniteNumber(new ArrayList<>(List.of(0))); // Adjusted to use List<Integer>
        InfiniteNumber one = new InfiniteNumber(new ArrayList<>(List.of(1))); // Adjusted to use List<Integer>

        for (InfiniteNumber i = new InfiniteNumber(new ArrayList<>(List.of(1))); i.compare(other) <= 0; i = i.add(one)) {
            result = result.add(this);
        }

        return result;
    }
    public InfiniteNumber divide(InfiniteNumber other) {
        InfiniteNumber count = new InfiniteNumber(new ArrayList<>(List.of(0)));
        InfiniteNumber temp = new InfiniteNumber(this.getInternalArray());

        while (temp.compare(other) >= 0) {
            temp = temp.subtract(other);
            count = count.add(new InfiniteNumber(new ArrayList<>(List.of(1))));
        }

        return count;
    }




    public static void main(String[] args) {
        // Example usage
        List<Integer> num1List = List.of(1, 2);
        List<Integer> num2List = List.of(1,0);
        InfiniteNumber num1 = new InfiniteNumber(num1List);
        InfiniteNumber num2 = new InfiniteNumber(num2List);

        InfiniteNumber sum = num1.add(num2);
        InfiniteNumber substract = num1.subtract(num2);
        InfiniteNumber multiply = num1.multiply(num2);
        InfiniteNumber divide = num1.divide(num2);
        System.out.println("Sum: " + sum.getNumberAsString());
        System.out.println("Sub: " + substract.getNumberAsString());
        System.out.println("mul: " + multiply.getNumberAsString());
        System.out.println("div: " + divide.getNumberAsString());
    }
}