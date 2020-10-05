import java.util.Random;

class Sender
{
    private String message;//message to be transmitted
    private String messageToBeTransmitted;//message to be transmitted
    private boolean errorAtTransmission; // boolean that shows if a error at the transmission occurred


    // Class constructor where p is the Binary Number of n+1 bits that is going to divide the message and k is the total bits of the message
    Sender(String p, int k)
    {
        this.message = generateRandomBinaryMessage(k);
        this.messageToBeTransmitted = "";
        this.errorAtTransmission = false;
        calculateMessageToBeTransmitted(message,p);
    }

    // Class constructor where p is the Binary Number of n+1 bits that is going to divide the message and k is the total bits of the message
    Sender(String p, int k, String message)
    {
        this.message = message;
        this.messageToBeTransmitted = "";
        this.errorAtTransmission = false;
        calculateMessageToBeTransmitted(message,p);
    }

    //  Function that generates random messages in case we want the messages to random
    private String generateRandomBinaryMessage(int k)
    {
        String binaryMessage = "";
        Random rand = new Random();

        for (int i = 0 ; i < k; i++)
        {
            // Obtain an integer number between [0 - 1].
            int randomNum = rand.nextInt(2);
            binaryMessage += Integer.toString(randomNum);
        }

        return binaryMessage;
    }

    // Calculates the message that the receiver gets after the transmittion
    private void calculateMessageToBeTransmitted(String num1, String num2)
    {
        int k = num1.length();
        int m = num2.length();
        int[] data = new int[k+m];
        int[] gen = new int[m];

        for(int i=0;i<k;i++)// Filling the array with the bits of num1
            data[i] = Character.getNumericValue(num1.charAt(i));

        for(int j=0;j<m;j++)// Filling the array with the bits of num2
            gen[j] = Character.getNumericValue(num2.charAt(j));

        for(int i=0;i<m-1;i++)// Adding n-1 zeros at the end of the starting message(for the 2^n*M)
            data[k+i] = 0;

        int[] rem = new int[m+k];// The array of the remainder
        for(int i=0;i<m;i++)
            rem[i]=data[i];

        int[] zeros = new int[m];
        for(int i=0;i<m;i++)
            zeros[i]=0;

        // Dividing 2^n*M with P using
        // Δυαδική πρόσθεση χωρίς κρατούμενο, oυσιαστικά η πράξη XOR
        int l,msb;
        for(int i=0;i<k;i++)
        {
            l = 0;
            msb=rem[i];
            for(int j=i;j<m+i;j++)
            {
                if(msb==0)
                    rem[j]=xor(rem[j],zeros[l]);
                else
                    rem[j]=xor(rem[j],gen[l]);
                l++;
            }
            rem[m+i]=data[m+i];
        }

        // Adding the reminder bits at the end of the message
        for(int i=k;i<k+m-1;i++)
        {
            data[i]=rem[i];
        }

        // The message that the Receiver recieves in order to divide it with P
        for(int i=0;i<k+m-1;i++)
            messageToBeTransmitted += Integer.toString(data[i]);
    }

    private static int xor(int x,int y)
    {
        if(x == y)
            return(0);
        else
            return(1);
    }

    boolean getErrorAtTransmission()
    {
        return  errorAtTransmission;
    }

    String getMessageToBeTransmitted()
    {
        StringBuilder message = new StringBuilder(messageToBeTransmitted);
        Random rand = new Random();
        for (int i = 0; i < message.length(); i++)
        {
            int randomNum = rand.nextInt(999);// We want each bit to have E = 10^-3 chance to be transmitted wrong
            if (randomNum == 0)
            {
                errorAtTransmission = true;
                message.setCharAt(i,(char)(((Character.getNumericValue(message.charAt(i)) + 1) % 2) + 48)); // Changing the bit value, adding 48 to convert from ASCII value to the actual integer
            }
        }

        return new String(message);
    }

    }
