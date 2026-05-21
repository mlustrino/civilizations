create database`civilizations`;

use civilizations; 
CREATE TABLE civilization_stats (civilization_id  INTEGER auto_increment PRIMARY KEY not null, name VARCHAR(100), wood_amount int default 0, iron_amount int default 0, 
food_amount int default 0, mana_amount int default 0, magicTower_counter int default 0, church_counter int default 0, farm_counter int default 0, 
smithy_counter int default 0, carpentry_counter int default 0, technology_defense_level int default 0, technology_attack_level int default 0, battles_counter int default 0); 

CREATE TABLE attack_units_stats (civilization_id INTEGER not null, unit_id INTEGER not null, type ENUM("Swordsman","Spearman","Crossbow","Cannon"), 
armor int default 0, base_damage int default 0, experience int default 0, sanctified boolean default false, PRIMARY KEY(civilization_id,unit_id),
CONSTRAINT fk_civ_attack FOREIGN KEY (civilization_id) References civilization_stats(civilization_id) on DELETE CASCADE);

CREATE TABLE defense_units_stats (civilization_id INTEGER not null, unit_id INTEGER NOT NULL, type ENUM("ArrowTower","Catapult","RocketLauncherTower"), 
armor int default 0, base_damage int default 0, experience int default 0, sanctified boolean default false, PRIMARY KEY(civilization_id,unit_id),
CONSTRAINT fk_civ_defense FOREIGN KEY (civilization_id) References civilization_stats(civilization_id) on DELETE CASCADE);

CREATE TABLE special_units_stats (civilization_id INTEGER not null, unit_id INTEGER NOT NULL, type ENUM("Magician","Priest"), 
armor int default 0, base_damage int default 0, experience int default 0, PRIMARY KEY(civilization_id,unit_id), CONSTRAINT fk_civ_special FOREIGN KEY (civilization_id) 
References civilization_stats(civilization_id) on DELETE CASCADE);

CREATE TABLE battle_stats(battle_id INTEGER AUTO_INCREMENT PRIMARY KEY NOT NULL, civilization_id INTEGER not null, num_battle int not null,
wood_acquired int default 0, iron_acquired int default 0, CONSTRAINT fk_battle_civ FOREIGN KEY (civilization_id) REFERENCES civilization_stats(civilization_id) on DELETE CASCADE);
 
 CREATE TABLE civilization_attack_stats ( civilization_id INTEGER NOT NULL, num_battle int not null, 
 unit_type ENUM("Swordsman","Spearman","Crossbow","Cannon") NOT NULL, initial_army int not null,
 drops int not null, PRIMARY KEY(civilization_id, num_battle, unit_type), 
 CONSTRAINT fk_report_civ FOREIGN KEY (civilization_id) REFERENCES civilization_stats(civilization_id) ON DELETE CASCADE);
 
 CREATE TABLE civilization_defense_stats ( civilization_id INTEGER NOT NULL, num_battle int not null,
 unit_type ENUM("ArrowTower","Catapult","RocketLauncherTower") NOT NULL, initial_army int not null,
 drops int not null, PRIMARY KEY(civilization_id, num_battle ,unit_type), CONSTRAINT fk_report_def FOREIGN KEY (civilization_id) REFERENCES civilization_stats(civilization_id) ON DELETE CASCADE);
 
 CREATE TABLE civilization_special_stats ( civilization_id INTEGER NOT NULL, num_battle int not null, 
 unit_type ENUM("Magician","Priest") NOT NULL, initial_army int not null,
 drops int not null, PRIMARY KEY(civilization_id, num_battle, unit_type), CONSTRAINT fk_report_spec FOREIGN KEY (civilization_id) REFERENCES civilization_stats(civilization_id) ON DELETE CASCADE);
 
 CREATE TABLE enemy_attack_stats ( civilization_id INTEGER NOT NULL, num_battle int not null,
 unit_type ENUM("Swordsman","Spearman","Crossbow","Cannon") NOT NULL, initial_army int not null,
 drops int not null, PRIMARY KEY(civilization_id, num_battle, unit_type), 
 CONSTRAINT fk_report_enemy FOREIGN KEY (civilization_id) REFERENCES civilization_stats(civilization_id) ON DELETE CASCADE);
 
 create table battle_log( civilization_id integer not null, num_battle int not null, num_line int not null, log_entry mediumtext, 
 PRIMARY KEY(civilization_id, num_battle, num_line), CONSTRAINT fk_log_civ FOREIGN KEY (civilization_id) REFERENCES civilization_stats(civilization_id) ON DELETE CASCADE);