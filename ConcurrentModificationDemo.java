import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
public class ConcurrentModificationDemo {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("One");
        list.add("Two");
        list.add("Three");
        try {
            // This will throw ConcurrentModificationException
            for (String item : list) {
                if (item.equals("Two")) {
                    list.remove(item); // Modifying the list during iteration
                }
            }
        } catch (ConcurrentModificationException e) {
            System.out.println("ConcurrentModificationException caught!");
        }
        // Proper way to modify the list during iteration
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            String item = iterator.next();
            if (item.equals("Two")) {
                iterator.remove(); // Using iterator's remove method
            }
        }

        System.out.println("List after modification: " + list);
    }
}
