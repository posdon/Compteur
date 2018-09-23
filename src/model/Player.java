package model;

public class Player {

	private String name;
	private int ID;
	
	public Player(String name, int iD) {
		super();
		this.name = name;
		ID = iD;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getID() {
		return ID;
	}
	
	public void setID(int iD) {
		ID = iD;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Player other = (Player) obj;
		if (ID != other.ID)
			return false;
		return true;
	}
	
	
}
