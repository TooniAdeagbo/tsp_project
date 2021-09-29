package cs211;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import  java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class tsp implements ActionListener {
	public static final Color BrightBlue = new Color (219,254,255);
	public static final Color ELighterBlue = new Color(219,244,255);
	private JTextArea outputtext;
	private static  JTextArea addresstext;
	private static ArrayList<String> orders;
	public tsp() {
		JPanel panel = new JPanel();//creates panel for GUI
		JPanel mapplace = new JPanel();
		JPanel paneldown = new JPanel();
		JPanel panelup = new JPanel();
		JPanel panelleft = new JPanel();
		JPanel panelright = new JPanel();
		JPanel textpanel = new JPanel();
		JFrame frame = new JFrame("MAP");//Creates frame for GUI and calls it MAP
		BufferedImage img = null;
		try {//please change this to file location you are using to store image given
		    img = ImageIO.read(new File("C:\\Users\\saman\\Downloads\\s_map.png"));
		} catch (IOException e) {}//gets image and catches for exception
		JLabel pic = new JLabel();
		if(img!=null) { pic = new JLabel(new ImageIcon(img));}//if img is null make it a blank panel
		if(pic!=null) {mapplace.add(pic);}

		JButton button = new JButton("Submit");//Button to  submit code
		button.addActionListener(this);//when button is clicked
		JLabel label = new JLabel ("Place Addresses Here");
		JLabel label2 = new JLabel ("Addresses in order here");	
		JLabel name = new JLabel ("by Oluwatooni Adeagbo");
		JLayeredPane layeredPane = new JLayeredPane();//so I can stack panels a top eachother
		addresstext = new JTextArea(25,40);//sets AddressText to 25 row and 40 columns
		outputtext= new JTextArea();
		frame.setSize(1500,1500);//sets size of frame to 1000X1000 pixels
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.add(panel);//adds all components to frame
		frame.add(mapplace);
		if(pic!=null) {frame.add(pic);}
		
		frame.add(paneldown);
		frame.add(panelup);
		frame.add(panelleft);
		frame.add(panelright);
		frame.add(button);
		frame.add(label);
		frame.add(label2);
		frame.add(name);
		frame.add(addresstext);
		frame.add (outputtext);
		frame.add(textpanel);
		textpanel.add(addresstext);
		panel.setBackground(BrightBlue);//sets colour for components
		paneldown.setBackground(ELighterBlue);
		panelup.setBackground(ELighterBlue);
		panelleft.setBackground(ELighterBlue);
		panelright.setBackground(ELighterBlue);
		textpanel.setBackground(ELighterBlue);
		mapplace.setBackground(BrightBlue);
		label.setBounds(100,-20, 300,100);//set Bounds and fonts for everything
		label.setFont(new Font("Arial", Font.PLAIN, 20));
		label2.setBounds(1025,-20,300,100);
		label2.setFont(new Font("Arial", Font.PLAIN, 20));
		name.setFont(new Font("Arial", Font.PLAIN, 20));
		name.setBounds(1025, 520, 250, 100);
		button.setBounds(85, 520, 250, 100);
		button.setFont(new Font("Arial", Font.PLAIN, 40));
		button.setBackground(new Color(255,255,255));
		layeredPane.add(panelright, JLayeredPane.POPUP_LAYER);//since the colours are layered i need to pop up everything
		layeredPane.add(panelleft, JLayeredPane.POPUP_LAYER);
		layeredPane.add(panelup, JLayeredPane.POPUP_LAYER);
		layeredPane.add(paneldown, JLayeredPane.POPUP_LAYER);
		layeredPane.add(panel,JLayeredPane.DEFAULT_LAYER);//this is base colour so it is at the lowest layer
		layeredPane.add(textpanel,JLayeredPane.POPUP_LAYER);
		layeredPane.add(mapplace,JLayeredPane.POPUP_LAYER);
		if(pic!=null) {layeredPane.add(pic,JLayeredPane.POPUP_LAYER);}
		
		panel.setSize(1500,1500);//make panel fit the entire page
		paneldown.setBounds(0,640,1500,50);
		panelup.setBounds(0, 0, 1500, 20);
		if(pic !=null) {
			pic.setBounds(520, 80, 400, 323);
		}

		panelleft.setBounds(450,0,30,1500);
		panelright.setBounds(950,0,30,1500);
		textpanel.setBounds(20, 45, 400, 450);
		outputtext.setBounds(1025, 45, 200, 400);
		addresstext.setLineWrap(false);//false Line Wrap so the addresses are presented properly
		outputtext.setLineWrap(true);//ture Linewrap so the orders order doesn't run through
		JScrollPane scroll = new JScrollPane(addresstext);//add scroll to addresstext area
		scroll.setWheelScrollingEnabled(true);//lets you scroll with wheel
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);//always show vertical and horizontal
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		addresstext.setEditable(true);//make addresstext editable
		outputtext.setEditable(false);//change outputtext to uneditable
		textpanel.add(scroll);//add scroll to text panel
		frame.add(layeredPane);//adds LayeredPane for layers colour
		frame.setVisible(true);//set element in frame and frame visible
		}

	public static void main (String args[]) {
		new tsp();	//makes new GUI (called tsp)
	}
	public static String realmain() {
		String input[]=addresstext.getText().split("\n");//fills array with adresstext input that is split for the index of starting a new line
	    orders = new ArrayList<>(Arrays.asList(input));	//makes order list the inputs of the array
		int ordersize = orders.size();//gets size of array
		ArrayList<Double> lat= latitude(orders);//make arraylist of all latitudes
		ArrayList<Double> lon= longitude(orders);//makes arraylist of all longitude
		ArrayList<Integer> orderno =  OrderNo(orders);//makes arraylist of orderno
		double startlat =53.381187;//get starting lat and lon of apache pizza
		double startlon= -6.5930467;
		double distance =0;
		int ordernumber = 0;
		int orderposition =0;
		String ordersorder="";
		int h =0;
		while(0!=orders.size()){//j doesnt increment as ordersize is continously getting smaller
			distance = Double.MAX_VALUE;//resets distance everytime
			for(int i =0; i<orders.size();i++) {
				double dist =getDistance(startlat,startlon, lat.get(i),lon.get(i));//gets all dsitances
					if(distance>dist) {//if distance is greater than current distance (dist)
						distance =dist;//current distance becomes smaller distance (dist)
						ordernumber =orderno.get(i);//get order no of actual order then get position of this so you can delete everything in that pos
						orderposition= orderno.indexOf(ordernumber);//get correct position of order
					}	
			}


		if(h==ordersize-1) {//if order is last order
			ordersorder+=Integer.toString(orderno.get(orderposition));
		}else {//if order isn't last order
			ordersorder+= Integer.toString(orderno.get(orderposition))+ ",";
		}
	 	orderno.remove(orderposition);//remove orders lat long and no so it cant get repeated
	 	orders.remove(orderposition);
	 	startlat = lat.get(orderposition);
	 	startlon = lon.get(orderposition);
	 	lat.remove(orderposition);
	 	lon.remove(orderposition);
		orders.trimToSize();//trim to right size so it doesn't get error or wrong result
		lat.trimToSize();
		lon.trimToSize();
		h++;//increment h for length of orders order
	}
		return ordersorder;//return anwser string
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		//when method is pressed
		String s= realmain();//method is called and gets string order
		outputtext.setText(s); //sets output text as string order
		addresstext.setEditable(false);//stops making addresstext editable
		
	}
	public static ArrayList<Double> latitude (ArrayList<String> orders) {// might make this into two different ones so its delete em
		ArrayList<Double> ar = new ArrayList<Double>();
		for(int i =0; i<orders.size();i++) {
			String a = orders.get(i);//get addressline at certain position
			a= a.substring(0,a.lastIndexOf(','));//gets substring for 0 to the last index of ,
			String b = a.substring(a.lastIndexOf(',')+1,a.length());//get substring from first ,+1 to the end of the string
			double lat = Double.parseDouble(b);//changes string to double
			ar.add(lat);		//add it to arraylist
		}
		return ar;//returns arraylist
	}
	public static ArrayList<Double> longitude (ArrayList<String> orders) {// might make this into two different ones so its delete em
		ArrayList<Double> ar = new ArrayList<Double>();
		for(int i =0; i<orders.size();i++) {
			String a = orders.get(i);
			String c = a.substring(a.lastIndexOf(',')+1,a.length());//gets substring  last ,+1 and goes to the end
			double lon = Double.parseDouble(c);//make string into double
			ar.add(lon);//adds to arraylist

			
		}
		return ar; //returns arraylist
	}
	

	public static double getDistance(double latitude1, double longitude1,double latitude2, double longitude2) {
        double dLat = Math.toRadians(latitude2 - latitude1);//gets distance
        double dLon = Math.toRadians(longitude2 - longitude1);
        latitude1 = Math.toRadians(latitude1);
        latitude2 = Math.toRadians(latitude2);
        double a = Math.pow(Math.sin(dLat / 2), 2) +
                   Math.pow(Math.sin(dLon / 2), 2) *
                   Math.cos(latitude1) *
                   Math.cos(latitude2);
        double rad = 6371;
        double distance = rad *2 * Math.asin(Math.sqrt(a));
        return distance; //returns distance in km
	}
	public static ArrayList<Integer> OrderNo (ArrayList<String> orders) {
		ArrayList<Integer> ordernum = new ArrayList<Integer>();
		for(int i =0; i<orders.size();i++) {
			String a = orders.get(i);//gets order string
			a=a.substring(0,a.indexOf(','));//gets order number
			int b =Integer.parseInt(a);//changes string to int
			ordernum.add(b);//adds to arraylist
		}
		return ordernum;//returns arraylist
	}
}