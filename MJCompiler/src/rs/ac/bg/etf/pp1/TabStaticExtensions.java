package rs.ac.bg.etf.pp1;

import rs.etf.pp1.symboltable.concepts.Struct;

public class TabStaticExtensions {

	// Symbol table extensions

	// bool type
	public static final Struct boolType = new Struct(Struct.Bool);
	
	// super class reference can be assigned to sub class reference
	public static boolean assignableTo(Struct src, Struct dst) {
		// check if they are assignable without classes extensions:
		if(src.assignableTo(dst)) {
			return true;
		}
		// we cannot declare those two struct nodes as non-assignable until we check its class extensions tree
		if(src.getKind() == Struct.Class && dst.getKind() == Struct.Class) {
			// both of them are classes
			Struct curr = src;
            while (curr != null) {

            	if (curr.equals(dst)) {
            		// one of the super classes is equal to destination
                    return true;
                }

                curr = curr.getElemType();
            }
		}
		
		return false;
	}
	
}
