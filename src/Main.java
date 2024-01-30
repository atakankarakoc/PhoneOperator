import java.util.*;
import java.util.concurrent.CountDownLatch;

public class Main {
    public static void main(String[] args) {
        // People list in 2 cities are created
        String [] aCity = {"Umur","Kemal","Ceyda","Dilan","Ahmet","Elif"};
        String [] bCity = {"Aleyna","Turan","Pınar","Özlem","Murat","Zeliha"};

        boolean bool = true;
        int rand_caller, rand_per_to_call;
        String caller = "";
        String person_to_call = "";
        // Uses HashMap class to store key-values
        Map<String, List<String>> MapList = new HashMap<>();
        // Creates a queue using the linked list class that implements the Queue interface.
        Queue<String> queue = new LinkedList<>();
        // The operator object of the operator class is created.
        Operator operator1 = new Operator();
        Operator operator2 = new Operator();

        while(bool){
            Random rand = new Random();
            // Phone calls are randomly generated.
            rand_caller = rand.nextInt(6);
            caller = aCity[rand_caller];
            rand_per_to_call = rand.nextInt(6);
            person_to_call = bCity[rand_per_to_call];

            // The caller and the people to be called are added on the hashmap.
            addHashMap(MapList, caller, person_to_call);
            // Calls are held in a queue, and the person whose turn it is to speak does the talking.
            waitingQueue(queue, caller, person_to_call);
            // a control mechanism to terminate the insertion when the total number
            // of 36 conversations is reached
            int total_num_of_values = 0;
            for (List<String> values : MapList.values()) {
                total_num_of_values += values.size();
                if(total_num_of_values == 36 ){
                    bool = false;
                }
            }
        }

        // a loop that will run until people added to the queue initiate calls and all calls are finished
        while (!queue.isEmpty()) {
            CountDownLatch latch = new CountDownLatch(2);
            String waitingQueueList = queue.poll();
            String [] list = waitingQueueList.split("- ");
            Call call = new Call(operator1, list[0], list[1], latch);
            call.start();
            if (!queue.isEmpty()) {
                waitingQueueList = queue.poll();
                String[] list2 = waitingQueueList.split("- ");
                Call call2 = new Call(operator2, list2[0], list2[1], latch);
                call2.start();
            }
        }

        // List who spoke to whom
        List<String> values = MapList.get("Umur");
        System.out.println("Individuals Umur is going to call: " + values);

        List<String> values2 = MapList.get("Kemal");
        System.out.println("Individuals Kemal is going to call: " + values2);

        List<String> values3 = MapList.get("Ceyda");
        System.out.println("Individuals Ceyda is going to call: " + values3);

        List<String> values4 = MapList.get("Dilan");
        System.out.println("Individuals Dilan is going to call: " + values4);

        List<String> values5 = MapList.get("Ahmet");
        System.out.println("Individuals Ahmet is going to call: " + values5);

        List<String> values6 = MapList.get("Elif");
        System.out.println("Individuals Elif is going to call: " + values6);
    }

    public static void addHashMap(Map<String, List<String>> MapList, String key, String value) {
        if (MapList.containsKey(key)) {
            List<String> values = MapList.get(key);
            if (!values.contains(value)) {
                values.add(value);
            }
            else{
                return;
            }
        }
        else {
            List<String> values = new ArrayList<>();
            values.add(value);
            MapList.put(key, values);
        }
    }

    public static void waitingQueue(Queue<String> queue, String key, String value) {
        String MapList = key + " - " + value;
        if (!queue.contains(MapList)) {
            queue.offer(MapList);
        } else {
            return;
        }
    }

}