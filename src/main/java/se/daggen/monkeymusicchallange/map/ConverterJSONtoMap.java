package se.daggen.monkeymusicchallange.map;

import org.json.JSONArray;

import se.daggen.common.converter.Converter;
import se.daggen.common.map.Coordinate;
import se.daggen.common.map.Map;

public class ConverterJSONtoMap implements Converter<JSONArray, Map<MapItem, Integer>>{

	@Override
	public Map<MapItem, Integer> convert(JSONArray jsonMap) {
		
		final int height = jsonMap.length();
		final int width = jsonMap.getJSONArray(0).length();
		
		Map<MapItem, Integer> map = new Map<>(width, height);
		
		for (int i = 0; i < height; i++) {
			JSONArray row = jsonMap.getJSONArray(i);
			for (int j = 0; j < width; j++) {
				String cell = row.getString(j);
				map.set(MapItem.valueOf(cell.toUpperCase()), Coordinate.ofArgs(i, j));
			}
		}
		
		return map;
	}
	
}
