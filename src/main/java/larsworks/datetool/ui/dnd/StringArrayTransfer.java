package larsworks.datetool.ui.dnd;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.eclipse.swt.dnd.ByteArrayTransfer;
import org.eclipse.swt.dnd.TransferData;

public class StringArrayTransfer extends ByteArrayTransfer {

	private static final Logger logger = Logger.getLogger(StringArrayTransfer.class);
	
	private static final String TYPENAME = "StringArrayType";
	private static final int TYPEID = registerType(TYPENAME);
	private static StringArrayTransfer _instance = new StringArrayTransfer();

	private StringArrayTransfer() {}
	
	public static StringArrayTransfer getInstance() {
		return _instance;
	}

	@Override
	protected int[] getTypeIds() {
		return new int[]{TYPEID};
	}

	@Override
	protected String[] getTypeNames() {
		return new String[]{TYPENAME};
	}

	@Override
	protected void javaToNative(Object object , TransferData data) {
		if(object == null || !(object instanceof StringArrayType)) {
			 return;
		}
		
		StringArrayType sat = (StringArrayType)object;
		
	 	if (isSupportedType(data)) {
	 		try {
	 			ByteArrayOutputStream out = new ByteArrayOutputStream();
	 			DataOutputStream writeOut = new DataOutputStream(out);
	 			writeOut.writeInt(sat.size());
	 			for(String str : sat.getList()) {
	 				writeOut.writeUTF(str);
	 			}
	 			byte[] buffer = out.toByteArray();
	 			writeOut.close();
	 
	 			super.javaToNative(buffer, data);
	 		} catch (IOException e) {
	 			logger.error("error when reading data.",e);
	 		}

	 	}

	}
	
	@Override
	protected Object nativeToJava(TransferData data) {
	 	if (isSupportedType(data)) {
	 		byte[] buffer = (byte[])super.nativeToJava(data);
	 		if (buffer == null) return null;
	 		StringArrayType sat = new StringArrayType();
	 		try {
	 			ByteArrayInputStream in = new ByteArrayInputStream(buffer);
	 			DataInputStream readIn = new DataInputStream(in);
	 			int length = readIn.readInt();
	 			for(int i = 0; i < length; i++) {
	 				sat.add(readIn.readUTF());
	 			}
	 			readIn.close();
	 		} catch (IOException e) {
	 			logger.error("could not read data", e);
	 		}
	 		return sat;
	 	}
		return null;
	}

}
