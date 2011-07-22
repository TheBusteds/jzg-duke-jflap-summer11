package JFLAPnew.formaldef.symbols;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import JFLAPnew.formaldef.FormalDefinition;
import JFLAPnew.formaldef.alphabets.IAlphabet;
import JFLAPnew.formaldef.symbols.variable.Variable;

public class SymbolString extends LinkedList<Symbol> implements Comparable<SymbolString> {

	public SymbolString(String in, FormalDefinition def){
		super();
		this.addAll(SymbolString.createFromString(in, def));
	}

	public SymbolString() {
		super();
	}

	public SymbolString(Symbol ... symbols) {
		super();
		for (Symbol s: symbols)
			this.add(s);
	}

	public String toString(){
		String string = "";
		for (Symbol s: this){
			string += s.toString();
		}
		return string;
	}
	
	public static LinkedList<? extends Symbol> createFromString(String in,
			FormalDefinition def) {
		
		String temp = "";
		SymbolString symbols = new SymbolString();
		
		for (int i = 0; i < in.length(); i++){
			temp += in.charAt(i);
			for (IAlphabet alph: def){
				if (alph.containsSymbolString(temp)){
					symbols.add(alph.createDesiredSymbol(temp));
					temp = "";
					break;
				}
			}
		}
		
		return symbols;
	}

	public <T extends Symbol> Set<T> getSymbolsOfClass(Class<T> clazz) {
		Set<T> results = new TreeSet<T>();
		for (Symbol s: this){
			if (s.getClass().isAssignableFrom(clazz))
				results.add((T) s);
		}
		
		return results;
	}
	
	public boolean equals(SymbolString o){
		return this.compareTo(o) == 0;		
	}

	@Override
	public SymbolString clone() {
		SymbolString string = new SymbolString();
		for (Symbol s: this)
			string.add(s.clone());
		return string;
	}

	@Override
	public int compareTo(SymbolString o) {
		Iterator<Symbol> me = this.iterator(),
		 		 other = o.iterator();
		while(me.hasNext() && other.hasNext()){
			Symbol sMe = me.next(),
				   sOther = other.next();
			
			if(sMe.compareTo(sOther) != 0)
					return sMe.compareTo(sOther);
		}
		
		if (!me.hasNext() && other.hasNext())
			return 1;
		if (me.hasNext() && !other.hasNext())
			return -1;
		
		return 0;
	}
}
