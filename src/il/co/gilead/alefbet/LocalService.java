package il.co.gilead.alefbet;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.SparseIntArray;

public class LocalService extends Service {
    // Binder given to clients
    private final IBinder mBinder = new LocalBinder();

    private SoundPool sp;
    private SparseIntArray audiosMap;
    public boolean finishedLoading;
    public boolean isTicking = false;
    public CountDownTimer timer;
    
    @Override
    public void onCreate() {
        super.onCreate();

        sp = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        new LoadAudiosInBackground().execute();

        timer = new CountDownTimer(5*60*1000, 10000){
    		public void onTick(long msUntilFinished){
    			isTicking = true;    			
    		}
    		
    		public void onFinish(){
    			isTicking = false;
    			stopSelf();
    		}
    	};
    }
    
    public void playAudio(int audio, float fSpeed) {
        AudioManager mgr = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        float streamVolumeCurrent = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);
        float streamVolumeMax = mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        float volume = streamVolumeCurrent / streamVolumeMax;

        if (audiosMap.get(audio) != 0)
        	sp.play(audiosMap.get(audio), volume, volume, 1, 0, fSpeed);
    }

	/**
	 * Class used for the client Binder.  Because we know this service always
	 * runs in the same process as its clients, we don't need to deal with IPC.
	 */
	public class LocalBinder extends Binder {
	    LocalService getService() {
	        // Return this instance of LocalService so clients can call public methods
	        return LocalService.this;
	    }
	}

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    /** method for clients */
    public void audioPlay(int audio){
        playAudio(audio, 1.0f);
    }
    
	private void loadAudios() {
		audiosMap = new SparseIntArray();
		for (int i=0; i<MainActivity.intNumOfPages; i++)
		{
	        audiosMap.put(i, sp.load(getApplicationContext(), getFileId(i), 1));
		}
	}
	
	private Integer getFileId(int index) {
		switch (index) {
		case 0:  return R.raw.l21;
		case 1:  return R.raw.l20;
		case 2:  return R.raw.l19;
		case 3:  return R.raw.l18;
		case 4:  return R.raw.l17;
		case 5:  return R.raw.l16;
		case 6:  return R.raw.l15;
		case 7:  return R.raw.l14;
		case 8:  return R.raw.l13;
		case 9:  return R.raw.l12;
		case 10: return R.raw.l11;
		case 11: return R.raw.l10;
		case 12: return R.raw.l9;
		case 13: return R.raw.l8;
		case 14: return R.raw.l7;
		case 15: return R.raw.l6;
		case 16: return R.raw.l5;
		case 17: return R.raw.l4;
		case 18: return R.raw.l3;
		case 19: return R.raw.l2;
		case 20: return R.raw.l1;
		case 21: return R.raw.l0;
		default: return null;
		}
	}

   private class LoadAudiosInBackground extends AsyncTask<Void, Void, Void>{

        @Override
        protected void onPreExecute() {
            finishedLoading = false ;
        }

        @Override
        protected Void doInBackground(Void... params) {
        	loadAudios();
			return null;
        }

       @Override
       protected void onPostExecute(Void result) {
    	   finishedLoading = true ;
       }       
   }

}