package com.akjava.lib.android.opengl;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;

public abstract class AbstractGLSurfaceView extends GLSurfaceView{

	public AbstractGLSurfaceView(Context context) {
		super(context);
        setFocusable(true);
        setFocusableInTouchMode(true);
	}
	
	protected AbstractRenderer render;
	
	public void setAbstractRender(AbstractRenderer render){
		super.setRenderer(render);
		this.render=render;
		//render.context=getContext();
	}
	

	
	/*
	public boolean onTrackballEvent (MotionEvent event){
		  System.out.println(event);
		    if(render==null){
		    	return true;
		    }
	        queueEvent(new Runnable(){
	            public void run() {
	            	  //TODO support
	            }});
	            return true;
	        }
	        */
	 
	 @Override
	   public boolean onTouchEvent(final MotionEvent event) {
		 
		  if(render!=null){
		    	
		    
		 switch( event.getAction() ){

		 case MotionEvent.ACTION_DOWN:
			 queueEvent(new Runnable(){
		            public void run() {
		            	render.onTouchDown(event.getX(), event.getY());
		            }});
		            return true;
		 

		 case MotionEvent.ACTION_UP:

		
			 queueEvent(new Runnable(){
		            public void run() {
		            	render.onTouchUp(event.getX(), event.getY());
		            }});
		            return true;

		 case MotionEvent.ACTION_MOVE:

			 queueEvent(new Runnable(){
		            public void run() {
		            	render.onTouchMove(event.getX(), event.getY());
		            }});
		            return true;

		 }
		  }else{
			  Log.i("myapp","touch:"+event.getAction()+", render is null.did you call setAbstractRender");
		  }

		 return super.onTouchEvent(event);

		 
		 
		  
	        
	        }
	   

	   /*
	    * (non-Javadoc)
	    * @see android.view.View#onKeyDown(int, android.view.KeyEvent)
	    */
	
	   @Override public boolean onKeyDown(final int keyCode,final  KeyEvent event) {
		  
		   if(render!=null){
			
			if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
				queueEvent(new Runnable() {
	                 public void run() {
	                	 render.actionUp();
	                 }});
				
				return true;
	    	}else if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
	    		queueEvent(new Runnable() {
	                 public void run() {
	                	 render.actionDown();
	                 }});
	    		return true;
	    	  
	    	}
	    	else if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
	    	queueEvent(new Runnable() {
                public void run() {
                	render.actionRight();
                }});
	    	
	    	return true;
	    		}else if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
	    			queueEvent(new Runnable() {
		                 public void run() {
		                	 render.actionLeft();
		                 }});
	    			
	    			return true;
	    	}else if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
	    		queueEvent(new Runnable() {
	                 public void run() {
	                	 render.actionCenter();
	                 }});
	    		
	    		return true;
	    	}
	    
		   }else{
			   Log.i("myapp","key:"+keyCode+", render is null.did you call setAbstractRender");
		   }
	         return super.onKeyDown(keyCode, event);
	     }
	   
	   
	
}
