/* A BACK UP COPY: BEFORE MAKING BIG ADJUSTMENTS TO cleanPouch()
// Author: Marcus Hacker
// Class for the Stormlight Archives currency of 'Spheres'.
// Has gem variations Diamond, Garnet, Ruby, Saphire, Emerald; Ordered from least to most valuable.
// These gems have different sizes that have different values similar to cents: Chip, Mark, Broam; Ordered from least to most valuable.
*/ 

// TODO: Add variable for whether the sphere is infused with stormlight or not

public class Sphere {
	/* Variables */

	private char gem;
	private char size;

	/* Constructors */
	// DEFAULT
	public Sphere() {
		gem = 'D';
		size = 'C';
	}

	// ALTERNATE
	public Sphere( char inGem, char inSize ) {
		setGem( inGem );
		setSize( inSize );
	}

	/* Accessors */
	public char getGem() {
		return gem;
	}

	public char getSize() {
		return size;
	}

	/* Mutators */
	public void setGem( char inGem ) {
		inGem = Character.toUpperCase( inGem );
		
		if( !( validGem( inGem ) ) )
			inGem = 'D';
		
		gem = inGem;
	}

	public void setSize( char inSize ) {
		inSize = Character.toUpperCase( inSize );
		
		if( !( validSize( inSize ) ) )
			inSize = 'C';
		
		size = inSize;
	}

	/* Methods */
	// Method to return value of the sphere, relative to a single diamond chip
	// PARAMETER: -
	// OUTPUT: Integer value
	public int getValue() {
		int value = 0;
		
		switch( gem ) {
			case 'D': // Diamond
				value = sizeToValue( size );
				break;
				
			case 'G': // Garnet, 5 times value
				value = sizeToValue( size ) * 5;
				break;
				
			case 'R': // Ruby, 10 times value
				value = sizeToValue( size ) * 10;
				break;
				
			case 'S': // Saphire, 25 times value
				value = sizeToValue( size ) * 25;
				break;
				
			case 'E': // Emerald, 50 times value
				value = sizeToValue( size ) * 50;
				break;
		}
		
		return value;
	} // END of getValue

	// Method to convert size to value (relative to 1 chip), mainly for the method getValue() to prevent redundant code.
	// PARAMETER: Character inSize
	// OUTPUT: Integer value
	private int sizeToValue( char inSize ) {
		int value = 0;
		switch( inSize ) {
			case 'C': // Chip: value of 1 chip
				value = 1;
				break;
				
			case 'M': // Mark: value of 5 chips
				value = 5;
				break;
				
			case 'B': // Broam: value of 20 chips
				value = 20;
				break;
		}
		
		return value;
	} // End of sizeToValue
	
	// Method to expand on sphere details, over the given parameters.
	// PARAMETER: Character inGem, Character inSize
	// OUTPUT: String (thisGem + " " + thisSize)
	public String sphereDetails( char inGem, char inSize ) {
		String thisGem, thisSize;
		
		thisGem = "";
		thisSize = "";
		
		switch( inGem ) {
			case 'D':
				thisGem = "Diamond";
				break;
				
			case 'G':
				thisGem = "Garnet";
				break;
			
			case 'R':
				thisGem = "Ruby";
				break;
			
			case 'S':
				thisGem = "Sapphire";
				break;
			
			case 'E':
				thisGem = "Emerald";
				break;
		}
		
		switch( inSize ) {
			case 'C':
				thisSize = "Chip";
				break;
			
			case 'M':
				thisSize = "Mark";
				break;
			
			case 'B':
				thisSize = "Broam";
				break;
		}
		
		return( thisGem + " " + thisSize );
	} // END of sphereDetails
	
	public boolean validGem( char inGem ) {
		return ( inGem == 'D' || inGem == 'G' || inGem == 'R' || inGem == 'S' || inGem == 'E');
	} // END of validGem
	
	public boolean validSize( char inSize ) {
		return ( inSize == 'C' || inSize == 'M' || inSize == 'B' );
	} // END of validSize
}
