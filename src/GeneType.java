package src;

public class GeneType implements Comparable<GeneType> {

	public int Fitness;
	public static int[] X= new int[86];
	public static int[] Y= new int[86];

    /**
     * @param o
     * @return
     */
    @Override
	public int compareTo(src.GeneType o) {
        int k;
        k = -1;
        if(o.Fitness < this.Fitness){			
			k=1;
		}else if(o.Fitness == this.Fitness){
			
			k=0;
		}else if(o.Fitness > this.Fitness){
			
			k=-1;
		}
		
		return k;
	}
}	