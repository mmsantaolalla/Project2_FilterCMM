package project;


public class Compound_pojo {
		// mi entidad compound es un POJO con 4 atributos (ver tipo) con getters (no hacen 
		// falta setters. SI HACE FALTA toString y Equals (identidad es el compound_id).
		//compound_id, formula, mass, name
	private Integer compound_id;

	private String formula;

	private Double mass;

	private String compound_name;
	
	public Compound_pojo (Integer compound_id, String formula, Double mass, String compound_name) {
		
		this.compound_id=compound_id;
		this.formula = formula;
		this.mass = mass;
		this.compound_name = compound_name;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Compound_pojo other = (Compound_pojo) obj;
		if (compound_id == null) {
			if (other.compound_id != null)
				return false;
		} else if (!compound_id.equals(other.compound_id))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return " Id: " + compound_id + " | name: " + compound_name + " | mass: " + mass + " | formula: " + formula ;
	}
	
	
	public Integer getCompound_id() {
		return compound_id;
	}

	public void setCompound_id(Integer compound_id) {
		this.compound_id = compound_id;
	}

	public String getFormula() {
		return formula;
	}

	public void setFormula(String formula) {
		this.formula = formula;
	}

	public Double getMass() {
		return mass;
	}

	public void setMass(Double mass) {
		this.mass = mass;
	}



}
