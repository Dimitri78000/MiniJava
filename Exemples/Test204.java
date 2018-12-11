class Test204{
    public static void main(String[] a){
    	System.out.println(new Test().start());
    }
}

class Test {
	Test test;
    int[] tab;
    public int start(){
	tab = new int[10];
	tab[tab.length-1]=6;
	test = this.next(tab);
	return tab[0];
    }

    public Test next(int[] t){
    	t[0]=tab[tab.length-1]*7;
    	return test;
    }
}
