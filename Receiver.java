class Receiver
{
    private String transmittedMessage; // Transmitted message
    private String p ; // Binary Number of n+1 bits

    Receiver(String p, String transmittedMessage)
    {
        this.p = p;
        this.transmittedMessage = transmittedMessage;
    }

    boolean isMessageCorrectlyTransmitted()
    {
        int k = transmittedMessage.length();
        int m = p.length();
        int[] data = new int[k+m];
        int[] gen = new int[m];

        for(int i=0;i<k;i++)// Filling the array with the bits of num1
            data[i] = Character.getNumericValue(transmittedMessage.charAt(i));

        for(int j=0;j<m;j++)// Filling the array with the bits of num2
            gen[j] = Character.getNumericValue(p.charAt(j));

       // for(int i=0;i<m-1;i++)// Adding n-1 zeros at the end of the starting message(for the 2^n*M)
           // data[k+i] = 0;

        int[] rem = new int[m + k];// The array of the remainder bits
        for(int i=0;i<m;i++)
            rem[i] = data[i];

        int[] zeros = new int[m];
        for(int i=0;i<m;i++)
            zeros[i]=0;

        // Dividing 2^n*M with P using
        // Δυαδική πρόσθεση χωρίς κρατούμενο, oυσιαστικά η πράξη XOR
        int l, msb;
        for(int i=0;i<k;i++)
        {
            l = 0;
            msb = rem[i];
            for(int j=i;j<m+i;j++)
            {
                if(msb == 0)
                    rem[j]=xor(rem[j],zeros[l]);
                else
                    rem[j]=xor(rem[j],gen[l]);
                l++;
            }
            rem[m+i]=data[m+i];
        }

        // Checks if there is a reminder
        for(int i=0;i<k+m-1;i++)
            if (rem[i] != 0)
                return false;

        return true; // Returns true if reminder is equal to 0
    }

    private static int xor(int x,int y)
    {
        if(x == y)
            return(0);
        else
            return(1);
    }
}
