package org.tju.wx.msgtype.response;

public class MusicMessage extends BaseMessage {
	// 音乐   
	private org.tju.wx.msgtype.response.Music Music;

	public org.tju.wx.msgtype.response.Music getMusic() {
		return Music;
	}

	public void setMusic(org.tju.wx.msgtype.response.Music music) {
		Music = music;
	}  

}
