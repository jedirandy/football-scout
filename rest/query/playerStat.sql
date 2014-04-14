SELECT p.player_id, player_name, p.team_id, team_name,
sum(time_played) as time_played,
sum(num_goal) as goal,
sum(num_assist) as assist,
sum(num_yellowcard) as yellowcard,
sum(num_redcard) as redcard,
sum(num_injury) as injury,
avg(num_ball_touched) as ball_touched,
count(1) as appearance,
avg(pass_precision) as pass_precision,
avg(shot_precision) as shot_precision,
avg(zone_x) as zone_x,
avg(zone_y) as zone_y,
FROM player p
INNER JOIN performance_fp pf 
ON p.player_id = pf.player_id
INNER JOIN team t
ON p.team_id = t.team_id
GROUP BY p.player_id