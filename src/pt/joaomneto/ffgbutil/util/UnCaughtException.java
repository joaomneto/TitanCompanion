package pt.joaomneto.ffgbutil.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.Date;
import java.util.Locale;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.util.Log;

public class UnCaughtException implements UncaughtExceptionHandler {
	private Context context;
	private static Context context1;

	public UnCaughtException(Context ctx) {
		context = ctx;
		context1 = ctx;
	}

	private void addInformation(StringBuilder message) {
		message.append("Locale: ").append(Locale.getDefault()).append('\n');
		try {
			PackageManager pm = context.getPackageManager();
			PackageInfo pi;
			pi = pm.getPackageInfo(context.getPackageName(), 0);
			message.append("Version: ").append(pi.versionName).append('\n');
			message.append("Package: ").append(pi.packageName).append('\n');
		} catch (Exception e) {
			Log.e("CustomExceptionHandler", "Error", e);
			message.append("Could not get Version information for ").append(context.getPackageName());
		}
		message.append("Phone Model: ").append(android.os.Build.MODEL).append('\n');
		message.append("Android Version: ").append(android.os.Build.VERSION.RELEASE).append('\n');
		message.append("Board: ").append(android.os.Build.BOARD).append('\n');
		message.append("Brand: ").append(android.os.Build.BRAND).append('\n');
		message.append("Device: ").append(android.os.Build.DEVICE).append('\n');
		message.append("Host: ").append(android.os.Build.HOST).append('\n');
		message.append("ID: ").append(android.os.Build.ID).append('\n');
		message.append("Model: ").append(android.os.Build.MODEL).append('\n');
		message.append("Product: ").append(android.os.Build.PRODUCT).append('\n');
		message.append("Type: ").append(android.os.Build.TYPE).append('\n');
	}

	@Override
	public void uncaughtException(Thread t, Throwable e) {
		try {
			StringBuilder report = new StringBuilder();
			Date curDate = new Date();
			report.append("Error Report collected on : ").append(curDate.toString()).append('\n').append('\n');
			report.append("Informations :").append('\n');
			addInformation(report);
			report.append('\n').append('\n');
			report.append("Stack:\n");
			final Writer result = new StringWriter();
			final PrintWriter printWriter = new PrintWriter(result);
			e.printStackTrace(printWriter);
			report.append(result.toString());
			printWriter.close();
			report.append('\n');
			report.append("**** End of current Report ***");
			Log.e(UnCaughtException.class.getName(), "Error while sendErrorMail" + report);
			sendErrorMail(report);
		} catch (Throwable ignore) {
			Log.e(UnCaughtException.class.getName(), "Error while sending error e-mail", ignore);
		}
	}

	/**
	 * This method for call alert dialog when application crashed!
	 */
	public void sendErrorMail(final StringBuilder errorContent) {
		new Thread() {
			@Override
			public void run() {
				try {
					StringBuilder body = new StringBuilder("Yoddle");
					body.append('\n').append('\n');
					body.append(errorContent).append('\n').append('\n');

					File dir = new File(Environment.getExternalStorageDirectory().getPath() + "/ffgbutil/");
					
					FileWriter fw = new FileWriter(new File(dir, "errorDump_"+System.currentTimeMillis()+".txt"));
					fw.write(body.toString());
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}
}