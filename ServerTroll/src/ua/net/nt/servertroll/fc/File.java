package ua.net.nt.servertroll.fc;

import java.util.Comparator;

public class File {

	boolean _dir;
	String _name;
	
	public File(String name, boolean dir) {
		_name = name;
		_dir = dir;
	}
	
	public String getName(){
		return _name;
	}

	public static Comparator<File> sComparator = new Comparator<File>() {
		@Override
		public int compare(File f1, File f2) {
			// Sort alphabetically by case, which is much cleaner
			return f1.getName()
					.compareTo(f2.getName());
		}
	};

}
