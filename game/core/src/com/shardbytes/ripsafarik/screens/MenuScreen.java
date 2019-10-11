package com.shardbytes.ripsafarik.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.shardbytes.ripsafarik.MainGame;

public class MenuScreen implements Screen {
	
	private MainGame mainScreen;
	
	private Stage uiStage;
	private Table uiTable;
	
	public MenuScreen(MainGame mainScreen) {
		this.mainScreen = mainScreen;
		
		uiStage = new Stage();
		Gdx.input.setInputProcessor(uiStage);
		
		uiTable = new Table();
		uiTable.setFillParent(true);
		
		uiStage.addActor(uiTable);
		uiTable.setDebug(true);
		
		addUIElements();
		
	}
	
	private void addUIElements() {
		Skin skin = new Skin();
		Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
		pixmap.setColor(Color.WHITE);
		pixmap.fill();
		
		skin.add("white", new Texture(pixmap));
		skin.add("defaultFont", new BitmapFont());
		skin.add("whiteColour", Color.WHITE);
		
		TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
		style.font = skin.getFont("defaultFont");
		style.up = skin.newDrawable("white", Color.DARK_GRAY);
		style.down = skin.newDrawable("white", Color.DARK_GRAY);
		style.checked = skin.newDrawable("white", Color.BLUE);
		style.over = skin.newDrawable("white", Color.LIGHT_GRAY);
		
		skin.add("default", style);
		
		Label.LabelStyle style1 = new Label.LabelStyle();
		style1.font = skin.getFont("defaultFont");
		style1.fontColor = skin.getColor("whiteColour");
		
		skin.add("default", style1);
		
		ScrollPane.ScrollPaneStyle style2 = new ScrollPane.ScrollPaneStyle();
		style2.background = skin.newDrawable("white", Color.DARK_GRAY);
		style2.corner = skin.newDrawable("white", Color.CYAN);
		style2.vScrollKnob = skin.newDrawable("white", Color.WHITE);
		
		skin.add("default", style2);
		
		TextButton loadButton = new TextButton("Load", skin);
		TextButton exitButton = new TextButton("Exit", skin);
		
		loadButton.addListener(new ClickListener(){
			
			@Override
			public boolean handle(Event event){
				//mainScreen.setScreen(new GameScreen());
				//TODO: fix repeating event
				System.out.println("yes");
				return true;
				
			}
			
		});
		
		exitButton.addListener(new ClickListener(){
			
			@Override
			public boolean handle(Event event){
				//Gdx.app.exit();
				System.out.println("yesnt");
				return true;
				
			}
			
		});
		
		uiTable.add(loadButton);
		uiTable.row().space(10);
		uiTable.add(exitButton);
		
		Table innerTable = new Table();
		ScrollPane scrollPane = new ScrollPane(innerTable, skin);
		
		InputListener stopTouchDown = new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
				event.stop();
				return false;
				
			}
			
		};
		
		innerTable.pad(10).defaults().expandX().fillX();
		
		for(int i = 0; i < 100; i++) {
			innerTable.add(new Label(i + ". label", skin));
			innerTable.row();
			
		}
		
		uiTable.add(scrollPane);
		
	}
	
	@Override
	public void show(){
		
	}
	
	@Override
	public void render(float delta){
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		uiStage.act(delta);
		uiStage.draw();
		
	}
	
	@Override
	public void resize(int width, int height){
		uiStage.getViewport().update(width, height, true);
		
	}
	
	@Override
	public void pause(){
		
	}
	
	@Override
	public void resume(){
		
	}
	
	@Override
	public void hide(){
		
	}
	
	@Override
	public void dispose(){
		
	}
	
}
