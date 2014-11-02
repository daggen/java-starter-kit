package se.daggen.monkeymusicchallange.map;

import java.util.Arrays;
import java.util.List;

public enum MapItem {
	
	USER, EMPTY, PLAYLIST, WALL, ALBUM, SONG, MONKEY;
	
	public static List<MapItem> IMPOSSIBLE_TERRAIN = Arrays.asList(USER, WALL);
	public static List<MapItem> NICE_ITEMS = Arrays.asList(PLAYLIST, ALBUM, SONG);
}
