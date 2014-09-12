package com.nomorehideandseek;

public class Location {

	int row;
	int floor;
	String section;
	
	
	
	
	public Location(int floor, String section,int row) {
		super();
		this.row = row;
		this.floor = floor;
		this.section = section;
	}
	
	
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public int getFloor() {
		return floor;
	}
	public void setFloor(int floor) {
		this.floor = floor;
	}
	public String getSection() {
		return section;
	}
	public void setSection(String section) {
		this.section = section;
	}
	
	public static Location CreateParkLocation(String scanText)
	{
		String[] locations=scanText.split("-");
		if (locations.length!=3) {
			return null;
		}
		try {
			return new Location(Integer.valueOf(locations[0]),locations[1],Integer.valueOf(locations[2]));
		} catch (Exception e) {
			return null;
		}
		
		
	}
	
	public String toString()
	{
		return String.format("Your Vehicle in %s floor, section \"%s\" and row %d", ordinal(floor),section,row);				
	}
	
	public static String ordinal(int i) {
	    String[] sufixes = new String[] { "th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th" };
	    switch (i % 100) {
	    case 11:
	    case 12:
	    case 13:
	        return i + "th";
	    default:
	        return i + sufixes[i % 10];

	    }
	}
	
	
}
