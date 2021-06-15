create table ex (
	compound_id INT NOT NULL PRIMARY KEY,
	C int NOT NULL default 0,
	N int NOT NULL default 0,
	Cl int NOT NULL default 0,
	O int NOT NULL default 0,
	H int NOT NULL default 0,
	P int NOT NULL default 0,
	S int NOT NULL default 0,
	formula varchar(100) not null default null,

	FOREIGN KEY(compound_id) REFERENCES compounds(compound_id) ON DELETE CASCADE, 
	INDEX compound_elements_C_index (C),
	INDEX compound_elements_N_index (N),
	INDEX compound_elements_Cl_index (Cl),
	INDEX compound_elements_O_index (O),
	INDEX compound_elements_H_index (H),
	INDEX compound_elements_P_index (P),
	INDEX compound_elements_S_index (S)

) ENGINE=INNODB;