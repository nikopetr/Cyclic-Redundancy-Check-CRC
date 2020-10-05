import java.util.Scanner;

public class Main {

    public static void main(String[] args)
    {
        System.out.println("Type 1 and press ENTER if you want to give your own message");
        System.out.println("Type 2 and press ENTER if you want to run for n times with random messages with length equal to 10");

        int choice;
        int totalErrorsAtTransmission = 0, totalErrorFoundByCRC = 0; // The numbers of errors found.
        int n = 1; // The number of loops of the program
        String P ;
        Scanner scanner = new Scanner(System.in);
        choice = scanner.nextInt();

        switch(choice){
            default:
                System.out.println("Give the binary message you want to send: ");
                String message = scanner.next(); // Reads the message to send from the user
                System.out.println("Give the binary number P for the CRC: ");
                P = scanner.next(); // Reads the P from the user

                Sender aSender = new Sender(P, message.length(), message);
                Receiver aReceiver = new Receiver(P, aSender.getMessageToBeTransmitted());

                if (aSender.getErrorAtTransmission()) // Prints if message was correctly transmitted
                    totalErrorsAtTransmission++;


                if (!aReceiver.isMessageCorrectlyTransmitted()) //Prints if CRC code tracked an error or not
                    totalErrorFoundByCRC++;

                break;

            case 2:
                System.out.println("Give the number of how many times you want the program to run: ");
                n = scanner.nextInt();

                int k = 0;
                P = "110101";
                while (k<n)
                {
                    Sender sender = new Sender(P, 10);
                    Receiver receiver = new Receiver(P, sender.getMessageToBeTransmitted());

                    if (sender.getErrorAtTransmission()) // Prints if message was correctly transmitted
                        totalErrorsAtTransmission++;


                    if (!receiver.isMessageCorrectlyTransmitted()) //Prints if CRC code tracked an error or not

                        totalErrorFoundByCRC++;

                    k++;
                }

                break;
        }

        System.out.println("Numbers of messages transmitted: " + n + ", with P = " + P + " and possibility of a bit to transmitted wrong = 10^(-3)");
        System.out.println("Percentage of messages with errors at Transmission: " + (double)totalErrorsAtTransmission/n*100 + "%");
        System.out.println("Percentage of messages detected as corrupted by CRC: " + (double)totalErrorFoundByCRC/n*100 + "%");

        if (totalErrorsAtTransmission != 0)
            System.out.println("Percentage of messages with errors at Transmission and not detected by CRC: " + (double)(totalErrorsAtTransmission-totalErrorFoundByCRC)/totalErrorsAtTransmission*100 + "%");
        else
            System.out.println("Percentage of messages with errors at Transmission and not detected by CRC: 0%");

    }
}
