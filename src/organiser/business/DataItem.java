package organiser.business;

import java.util.UUID;

public class DataItem<T extends DataItemValue> {
	private String label;
	private T value;
	public boolean isEdit;
	
	public DataItem(String label, T data){
		this.label = label;
		this.value = data;
		this.isEdit = true;
	}
	
	public String getLabel(){
		return label;
	}
	public T getValue(){
		return value;
	}
	public void setValue(T value){
		this.value = value;
	}
}
