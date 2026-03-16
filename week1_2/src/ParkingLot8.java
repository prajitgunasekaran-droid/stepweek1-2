import java.util.*;

enum SpotStatus {
    EMPTY,
    OCCUPIED,
    DELETED
}

class ParkingSpot {

    String licensePlate;
    long entryTime;
    SpotStatus status;

    ParkingSpot() {
        status = SpotStatus.EMPTY;
    }
}

class ParkingLot {

    ParkingSpot[] table;
    int capacity;
    int occupied = 0;
    int totalProbes = 0;
    int operations = 0;

    public ParkingLot(int size) {

        capacity = size;
        table = new ParkingSpot[size];

        for (int i = 0; i < size; i++)
            table[i] = new ParkingSpot();
    }

    private int hash(String plate) {
        return Math.abs(plate.hashCode()) % capacity;
    }

    // Park vehicle
    public void parkVehicle(String plate) {

        int index = hash(plate);
        int probes = 0;

        while (table[index].status == SpotStatus.OCCUPIED) {

            index = (index + 1) % capacity;
            probes++;
        }

        table[index].licensePlate = plate;
        table[index].entryTime = System.currentTimeMillis();
        table[index].status = SpotStatus.OCCUPIED;

        occupied++;
        totalProbes += probes;
        operations++;

        System.out.println("Vehicle " + plate +
                " assigned spot #" + index +
                " (" + probes + " probes)");
    }

    // Exit vehicle
    public void exitVehicle(String plate) {

        int index = hash(plate);

        while (table[index].status != SpotStatus.EMPTY) {

            if (table[index].status == SpotStatus.OCCUPIED &&
                    plate.equals(table[index].licensePlate)) {

                long duration =
                        System.currentTimeMillis() - table[index].entryTime;

                double hours = duration / (1000.0 * 60 * 60);

                double fee = hours * 5; // $5 per hour

                table[index].status = SpotStatus.DELETED;
                occupied--;

                System.out.println(
                        "Vehicle " + plate +
                                " exited from spot #" + index +
                                ", Duration: " +
                                String.format("%.2f", hours) +
                                " hours, Fee: $" +
                                String.format("%.2f", fee)
                );

                return;
            }

            index = (index + 1) % capacity;
        }

        System.out.println("Vehicle not found.");
    }

    // Statistics
    public void getStatistics() {

        double occupancy =
                (occupied * 100.0) / capacity;

        double avgProbes =
                operations == 0 ? 0 :
                        (double) totalProbes / operations;

        System.out.println("\nParking Statistics:");
        System.out.println("Occupancy: " +
                String.format("%.2f", occupancy) + "%");
        System.out.println("Average Probes: " +
                String.format("%.2f", avgProbes));
    }
}

class ParkingMain {

    public static void main(String[] args) throws Exception {

        ParkingLot lot = new ParkingLot(10);

        lot.parkVehicle("ABC-1234");
        lot.parkVehicle("ABC-1235");
        lot.parkVehicle("XYZ-9999");

        Thread.sleep(2000);

        lot.exitVehicle("ABC-1234");

        lot.getStatistics();
    }
}