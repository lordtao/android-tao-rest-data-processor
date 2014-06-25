package ua.at.tsvetkov.dataprocessor.interfaces;

import java.io.IOException;
import java.io.InputStream;

public interface InputStreamDataInterface {

	public void fillFromInputStream(InputStream in) throws IOException;

}
