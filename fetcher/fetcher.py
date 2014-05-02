#!/usr/bin/python

'''
Created on Apr 4, 2014
@Course: CS6242 
'''

import requests
import xml.etree.ElementTree as ET
import csv


def getMatch(id):     
    url = "http://playground.opta.net/?game_id="+str(id)+"&feed_type=F7"
    res = requests.get(url)
    data = res.content
    return data

def getEvent(id):
    url = "http://playground.opta.net/?game_id="+str(id)+"&feed_type=F24"
    res = requests.get(url)
    data = res.content
    return data

    
def saveCSV1(file,data):
    with open(file, 'wb') as f:
        wtr = csv.writer(f, delimiter=',')
        for key, value in data.items():
            item = []
            item.append(key);
            item.extend(value)
            wtr.writerow(item)
            
def saveCSV2(file,data):
    with open(file, 'wb') as f:
        wtr = csv.writer(f, delimiter=',')
        for key, value in data.items():
            item = []      
            item.extend(list(key));
            item.extend(value)
            wtr.writerow(item)

if __name__=="__main__":
    
    BeginID = 131897
    EndID = 131986
    Data_Match=dict();  Data_Team=dict(); Data_Player=dict(); Data_PerfFP=dict(); Data_PerfGK=dict()
    Data_FPlayer=dict(); Data_GKeeper=dict();Player_Pass = dict()
    
    #------------------------------- F7 feed ---------------------------------#
   
    for id in range(BeginID, EndID+1):  
        '''131956/131986 is missing'''
        temp_match = []; temp_player=[]
        data = getMatch(id)
        root = ET.fromstring(data)
        if root.tag != 'response':
            # Get Match info
            SoccerDocument = root.find('SoccerDocument')
            match_id = SoccerDocument.get('uID')[1:]
                   
            for Stat in SoccerDocument.find('Competition').findall('Stat'):
                if(Stat.get('Type')=='matchday'):
                    matchday = Stat.text                
            for TeamData in SoccerDocument.find('MatchData').findall('TeamData'):
                if(TeamData.get('Side')=='Home'):
                    team_home = TeamData.get('TeamRef')[1:]
                    score_home = TeamData.get('Score')
                if(TeamData.get('Side')=='Away'):
                    team_away = TeamData.get('TeamRef')[1:]
                    score_away = TeamData.get('Score')
        
            Data_Match[match_id]= [matchday,team_home,team_away,score_home,score_away]
                
            # Get Team/Player info         
            for Team in SoccerDocument.findall('Team'):
               team_id = Team.get('uID')[1:]
               team_name = Team.find('Name').text
                   
               for Player in Team.findall('Player'):
                   player_id = Player.get('uID')[1:]
                   player_name = Player.find('PersonName')[0].text + " " + Player.find('PersonName')[1].text
                   Data_Player[player_id]= [team_id,player_name.encode('utf-8')]
          
            Data_Team[team_id]= [team_name.encode('utf-8')]
           
    #------------------------------- F24 feed ---------------------------------#

    for id in range(BeginID, EndID+1):
        print id
        data = getEvent(id)
        root = ET.fromstring(data)
        list_player = [];list_position = [];list_tshirt=[]; antiGK=dict()
        Game = root.find('Game')
        match_id = Game.get('id')
        away_team_id = Game.get('away_team_id')
        home_team_id = Game.get('home_team_id')
           
        # Get player/position list for initialization 
        for Event in Game.findall('Event'):      
            if(Event.get('type_id') =="34"):
                for Q in Event.findall('Q'):
                    if Q.get('qualifier_id') =='30':
                        list_player.extend(p for p in Q.get('value').split(", ")) 
                    if Q.get('qualifier_id') =='44':
                        list_position.extend(p for p in Q.get('value').split(", ")) 
                    if Q.get('qualifier_id') =='59':
                        list_tshirt.extend(p for p in Q.get('value').split(", ")) 
           
        # Initialize                      
        for i,p in enumerate(list_player):
            Data_FPlayer[(p,match_id)] = [0.0]*63
            Data_GKeeper[(p,match_id)] = [0]*6 
            if(i%18 > 10):
                #entry time
                Data_FPlayer[(p,match_id)][60] = 90.0
            #exit time
            Data_FPlayer[(p,match_id)][61] = 90.0
            #position
            Data_FPlayer[(p,match_id)][62] = float(list_position[i])
       
        antiGK[home_team_id] = list_player[18]
        antiGK[away_team_id] = list_player[0]
           
           
        # Update Stats (Raw data)
        for Event in Game.findall('Event'):  
               
            period_id = Event.get('period_id')              
            if(period_id =="1" or period_id =="2"):         
                player_id = Event.get('player_id')
                type = Event.get('type_id') 
                   
                if (player_id != None):
                       
                    # Field Player
                    x = Event.get('x'); y = Event.get('y');
                    if (period_id=='2'):
                        x = 100.0 - float(x)
                        y = 100.0 - float(y)
                           
                    #sum of zone_x
                    Data_FPlayer[(player_id,match_id)][57]+=float(x)
                    #sum of zone_y
                    Data_FPlayer[(player_id,match_id)][58]+=float(y) 
                    #sum of zone_count
                    Data_FPlayer[(player_id,match_id)][59]+=1  
                               
                    if(type=='1'):
                        #number of pass
                        count = 0
                        for Q in Event.findall('Q'):
                            if Q.get('qualifier_id') in ['2','5','6','107','123','124']:
                                count+= 1
                        if count == 0:
                            Data_FPlayer[(player_id,match_id)][0]+=1
                             #pass missed  
                            if(Event.get('outcome')=='0'):
                                Data_FPlayer[(player_id,match_id)][3]+=1
                                                 
                        count = 0
                        count2 = 0
                        for Q in Event.findall('Q'):
                             if (Q.get('qualifier_id') in ['5','6']): 
                                 count+=1
                        for Q in Event.findall('Q'):
                             if (Q.get('qualifier_id') =='2'): 
                                 count2+=1
                        if count == 0 and count2 ==1:
                            #cross
                             Data_FPlayer[(player_id,match_id)][1]+=1
                                
                        #ball touched
                        count = 0
                        for Q in Event.findall('Q'):
                             if (Q.get('qualifier_id')=='123'):
                                 count+=1
                        if count == 0:
                            Data_FPlayer[(player_id,match_id)][2]+=1
                      
                         
                       
                       
                    if(type=='2'):
                        #offside                   
                        for Q in Event.findall('Q'):
                            if Q.get('qualifier_id') =='7':
                                p = Q.get('value')
                                Data_FPlayer[(p,match_id)][4]+=1
                           
                    if(type in ['2','3','7','8','9','10','11','12','13','14','15','16','41','42','50','52','54','61']):
                        #ball touched
                        Data_FPlayer[(player_id,match_id)][2]+=1
                           
                    if(type=='4'): 
                        #number of fouls
                        if(Event.get('outcome')=='0'):
                            Data_FPlayer[(player_id,match_id)][5]+=1
                                                
                    if(type=='7'): 
                        #number of successful tackles
                        Data_FPlayer[(player_id,match_id)][6]+=1
                       
                    if(type=='8'):        
                        #number of interception      
                        Data_FPlayer[(player_id,match_id)][7]+=1   
                       
                    if(type in ['13','14','15','16']):        
                        #number of shots     
                        Data_FPlayer[(player_id,match_id)][8]+=1
                           
                        #number of freekick shots
                        for Q in Event.findall('Q'):
                             if (Q.get('qualifier_id')=='26'): 
                                   Data_FPlayer[(player_id,match_id)][9]+=1
                       
                    if(type=='16'):  
                        #number of goal non-stopped
                        team_id = Event.get('team_id')
                        Data_GKeeper[(antiGK[team_id],match_id)][0]+=1
                        #number of assists
                        if(Event.get('outcome')=='1'):
                            for Q in Event.findall('Q'):
                                if (Q.get('qualifier_id')=='55'): 
                                    event_id = Q.get('value')
                                    for e in root.iter('Event'):
                                        if (e.get('event_id')== event_id):
                                            p = e.get('player_id')
                                            Data_FPlayer[(p,match_id)][10]+=1                                 
                           
                        if(Event.get('outcome')=='1'):
                            count = 0
                            for Q in Event.findall('Q'):
                                 if (Q.get('qualifier_id')=='28'):
                                     count+=1
                            if count == 0:
                                #number of goals
                                Data_FPlayer[(player_id,match_id)][11]+=1
                                count2 = 0
                                for Q in Event.findall('Q'):
                                    if (Q.get('qualifier_id')=='22'):
                                        #open-play goal
                                        Data_FPlayer[(player_id,match_id)][12]+=1
                                    if (Q.get('qualifier_id')=='15'):
                                        #head goal
                                        Data_FPlayer[(player_id,match_id)][13]+=1                  
                                    if (Q.get('qualifier_id') in ['24','9']):
                                        count2+=1                            
                                if count2!=0:
                                    #freekick goal
                                    Data_FPlayer[(player_id,match_id)][14]+=1                    
       
                    if(type in ['15','16']):
                        #on-target shot
                        Data_FPlayer[(player_id,match_id)][15]+=1
                       
                    if(type=='17'):                  
                        count = 0
                        for Q in Event.findall('Q'):
                            if (Q.get('qualifier_id') in ['31','32']):
                                count +=1 
                            if (Q.get('qualifier_id')=='33'):
                                #red cards
                                Data_FPlayer[(player_id,match_id)][17]+=1
                        if count!=0:
                            #yellow cards
                            Data_FPlayer[(player_id,match_id)][16]+=1                  
                       
                    if(type=='44'):
                        #aerial duel
                        Data_FPlayer[(player_id,match_id)][18]+=1
                        if(Event.get('outcome')=='1'):
                            #aerial duel won
                            Data_FPlayer[(player_id,match_id)][19]+=1
                       
                    if(type in ['18','32','34','36','40']):
                        count = 0
                        for Q in Event.findall('Q'):
                            if (Q.get('qualifier_id')=='41'):
                                count+=1
                        if count!=0:
                            for Q in Event.findall('Q'):
                                if (Q.get('qualifier_id')=='53'): 
                                    p = Q.get('value')
                                    #number of injuries
                                    Data_FPlayer[(p,match_id)][20]+=1
                       
                    if(type=='1'):
                        if(Event.get('outcome')=='1'):
                            event_id = int(Event.get('event_id'))+1
                            team_id = Event.get('team_id')
                            for e in root.iter('Event'):
                                if (e.get('event_id')== str(event_id)):
                                    p = e.get('player_id')
                                    t = e.get('team_id')
                                    if p!=None and t==team_id:
                                        idx = list_player.index(p)
                                        # 36 players
                                        Data_FPlayer[(player_id,match_id)][idx+21]+=1
                                        Player_Pass.setdefault((match_id,team_id,player_id,p), [0]*1)
                                        Player_Pass[(match_id,team_id,player_id,p)][0]+=1    
                       
                    if(type=='18'):
                        #exit
                        Data_FPlayer[(player_id,match_id)][61]=float(Event.get('min'))
                       
                    if(type=='19'):   
                        #substitute
                        Data_FPlayer[(player_id,match_id)][60]= float(Event.get('min'))
                        for Q in Event.findall('Q'):
                            if (Q.get('qualifier_id')=='44'):
                                positionName = Q.get('value')
                                if(positionName == 'Goalkeeper'):
                                    Data_FPlayer[(player_id,match_id)][62]=1.0
                                    if Event.get('team_id') == home_team_id:
                                        antiGK[away_team_id] = player_id
                                    else :
                                        antiGK[home_team_id] = player_id
                                elif (positionName == 'Defender'):
                                    Data_FPlayer[(player_id,match_id)][62]=2.0
                                elif (positionName == 'Midfielder'):
                                    Data_FPlayer[(player_id,match_id)][62]=3.0
                                elif (positionName == 'Forward'):
                                    Data_FPlayer[(player_id,match_id)][62]=4.0
                                       
                    if(type=='10'):  
                        count=0
                        for Q in Event.findall('Q'):
                            if (Q.get('qualifier_id') == '94'):    
                                count+=1
                        if count==0:
                            #number of save
                            Data_GKeeper[(player_id,match_id)][1]+=1
                       
                    if(type=='11'):
                        if (Event.get('outcome')=='1'):
                            #number of cross claimed
                            Data_GKeeper[(player_id,match_id)][2]+=1
                        else:
                            #number of error
                            Data_GKeeper[(player_id,match_id)][4]+=1   
                               
                    if(type=='41'):   
                        #number of punch
                        Data_GKeeper[(player_id,match_id)][3]+=1   
                                              
                    if(type=='59'):
                        #number of keeper sweeper
                        Data_GKeeper[(player_id,match_id)][5]+=1
                        if (Event.get('outcome')=='0'):
                            #number of error
                            Data_GKeeper[(player_id,match_id)][4]+=1  
                              
                       
                       
                                       
        # Transform data                      
        for i,p in enumerate(list_player):
               
            #-----------------------Field Player--------------------------# 
            num_pass = Data_FPlayer[(p,match_id)][0] #number of pass
            num_cross = Data_FPlayer[(p,match_id)][1] #number of cross
            num_balltouched = Data_FPlayer[(p,match_id)][2] #number of ball touched
            num_goal = Data_FPlayer[(p,match_id)][11] #number of goal
            num_headgoal = Data_FPlayer[(p,match_id)][13] #number of head goal
            num_freekickgoal = Data_FPlayer[(p,match_id)][14] #number of freekick goal 
            num_normalgoal = Data_FPlayer[(p,match_id)][12] #number of normal goal
            num_shot = Data_FPlayer[(p,match_id)][8] #number of shot
            num_freekickshot = Data_FPlayer[(p,match_id)][9] #number of freekick shot
            num_tackle = Data_FPlayer[(p,match_id)][6] #number of successful tackle
            num_interception = Data_FPlayer[(p,match_id)][7] #number of interception
            num_foul = Data_FPlayer[(p,match_id)][5] #number of foul
            num_offside = Data_FPlayer[(p,match_id)][4] #number of offside
            num_assist = Data_FPlayer[(p,match_id)][10] #number of assist
            num_yellowcard = Data_FPlayer[(p,match_id)][16] #number of yellow card
            num_redcard = Data_FPlayer[(p,match_id)][17] #number of red card
            num_injury = Data_FPlayer[(p,match_id)][20] #number of injury
               
            temp_list = Data_FPlayer[(p,match_id)][21:56]
            temp_list.sort()         
            coplayer1 = list_player[Data_FPlayer[(p,match_id)][21:56].index(temp_list[-1])]
            coplayer2 = list_player[Data_FPlayer[(p,match_id)][21:56].index(temp_list[-2])]
            coplayer3 = list_player[Data_FPlayer[(p,match_id)][21:56].index(temp_list[-3])]
            tshirt = int(list_tshirt[i]) #t-shirt
            position = int(Data_FPlayer[(p,match_id)][62])#position
            if(Data_FPlayer[(p,match_id)][0]!=0.0):
                pass_precision = (Data_FPlayer[(p,match_id)][0]-Data_FPlayer[(p,match_id)][3])/Data_FPlayer[(p,match_id)][0] #pass precision
            else: 
                pass_precision = -1.0
            if(Data_FPlayer[(p,match_id)][8]!=0.0):
                shot_precision = Data_FPlayer[(p,match_id)][15]/Data_FPlayer[(p,match_id)][8]
            else:
                shot_precision = -1.0
            if(Data_FPlayer[(p,match_id)][18]!=0.0):
                aerialduel_won = Data_FPlayer[(p,match_id)][19]/Data_FPlayer[(p,match_id)][18]
            else: 
                aerialduel_won = -1.0
            timeplayed = Data_FPlayer[(p,match_id)][61] - Data_FPlayer[(p,match_id)][60]
            if Data_FPlayer[(p,match_id)][59]!=0.0:
                zone_x = Data_FPlayer[(p,match_id)][57]/Data_FPlayer[(p,match_id)][59]  
                zone_y = Data_FPlayer[(p,match_id)][58]/Data_FPlayer[(p,match_id)][59] 
            else:
                zone_x = 0.0
                zone_y= 0.0
               
                     
            if(position!=1 and timeplayed!=0.0):
                Data_PerfFP[(p,match_id)]=[]
                Data_PerfFP[(p,match_id)].extend([num_pass,num_cross,num_balltouched,num_goal,num_headgoal, num_freekickgoal, num_normalgoal, num_shot, num_freekickshot
                              ,num_tackle,num_interception,num_foul,num_offside,num_assist,num_yellowcard,num_redcard,num_injury,coplayer1,coplayer2,coplayer3,tshirt,position
                              ,pass_precision,shot_precision,aerialduel_won,timeplayed,zone_x,zone_y])
               
            #-----------------------Goal Keeper--------------------------#    
            num_nonestoppedgoal = Data_GKeeper[(p,match_id)][0]
            num_save = Data_GKeeper[(p,match_id)][1]
            num_crossclaimed = Data_GKeeper[(p,match_id)][2]
            num_punch = Data_GKeeper[(p,match_id)][3]
            num_error = Data_GKeeper[(p,match_id)][4]
            num_keepersweeper = Data_GKeeper[(p,match_id)][5]
               
            if(position==1):
                Data_PerfGK[(p,match_id)]=[]
                Data_PerfGK[(p,match_id)].extend([num_nonestoppedgoal,num_save,num_crossclaimed,num_punch,num_error,num_keepersweeper ])
              
            
    # Save the file
    #saveCSV1('Match.csv',Data_Match)
    #saveCSV1('Team.csv',Data_Team)
    #saveCSV1('Player.csv',Data_Player)
#     saveCSV2('Performance-FP.csv',Data_PerfFP)
#     saveCSV2('Performance-GK.csv',Data_PerfGK)
    saveCSV2('Player-Pass.csv',Player_Pass)
    
    
    
