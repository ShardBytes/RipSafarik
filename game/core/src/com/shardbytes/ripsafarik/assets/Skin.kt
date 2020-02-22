package com.shardbytes.ripsafarik.assets

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.TextButton

object Skin {
	
	val skin = Skin()
	val pixmap = Pixmap(1, 1, Pixmap.Format.RGBA8888)
	val style = TextButton.TextButtonStyle()
	val style1 = Label.LabelStyle()
	val style2 = ScrollPane.ScrollPaneStyle()
	
	fun create() {
		pixmap.setColor(Color.WHITE)
		pixmap.fill()
		
		skin.add("white", Texture(pixmap))
		skin.add("defaultFont", BitmapFont())
		skin.add("whiteColour", Color.WHITE)
		
		style.font = skin.getFont("defaultFont")
		style.up = skin.newDrawable("white", Color.DARK_GRAY)
		style.down = skin.newDrawable("white", Color.DARK_GRAY)
		style.checked = skin.newDrawable("white", Color.BLUE)
		style.over = skin.newDrawable("white", Color.LIGHT_GRAY)
		skin.add("default", style)
		
		style1.font = skin.getFont("defaultFont")
		style1.fontColor = skin.getColor("whiteColour")
		skin.add("default", style1)
		
		style2.background = skin.newDrawable("white", Color.DARK_GRAY)
		style2.corner = skin.newDrawable("white", Color.CYAN)
		style2.vScrollKnob = skin.newDrawable("white", Color.WHITE)
		skin.add("default", style2)
	}
}