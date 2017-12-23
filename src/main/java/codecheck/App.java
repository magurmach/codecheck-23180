package codecheck;

import codecheck.util.Base64;
import codecheck.util.FileUtil;

import java.io.IOException;
import java.security.InvalidParameterException;

public class App {
	public static void main(String[] args) throws IOException {
		boolean decoding = false;
		boolean encoding = false;
		String inputFileName = "";
		String outputFileName = "";
		int i;

		for (i = 0; i < args.length; i++) {
			switch (args[i]) {
				case "-d":
					decoding = true;
					break;
				case "-e":
					encoding = true;
					break;
				case "-i":
					inputFileName = args[++i];
					break;
				case "-o":
					outputFileName = args[++i];
					break;
				default:
					throw new InvalidParameterException("Invalid argument!");
			}
		}

		if (!decoding) {
			encoding = true;
		}

		byte[] inputStream;
		byte[] outputStream;

		inputStream = FileUtil.readFile(inputFileName);

		if (encoding) {
			outputStream = Base64.encode(inputStream);
		} else {
			outputStream = Base64.decode(inputStream);
		}

		System.out.println(new String(outputStream));

		FileUtil.writeFile(outputFileName, outputStream);
	}
}
