-- apply changes
create table bedwars_stats (
  id                            uuid not null,
  wins                          integer not null,
  final_kills                   integer not null,
  beds_broken                   integer not null,
  losses                        integer not null,
  beds_lost                     integer not null,
  kills                         integer not null,
  deaths                        integer not null,
  final_deaths                  integer not null,
  damage_dealt                  float not null,
  damage_taken                  float not null,
  constraint pk_bedwars_stats primary key (id)
);

