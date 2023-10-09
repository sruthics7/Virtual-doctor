

import os
from flask import *
import pymysql
from werkzeug.utils import secure_filename
import time
from src.myknn import  prep
from src.dbconnector import *
con=pymysql.connect(host='localhost',port=3306,user='root',passwd='',db='virtual doctor with smart pharmacy')
cmd=con.cursor()
app=Flask(__name__)
con=pymysql.connect(host="localhost",user="root",password="",port=3306,db="virtual doctor with smart pharmacy")
Cmd=con.cursor()









@app.route('/uploadphoto',methods=['post'])
def uploadphoto():
    try:
        uid=request.form['lid']
        photo=request.files['file']
        files=secure_filename(photo.filename)
        photo.save(os.path.join('static/photo',files))
        final = prep("static/photo/" + files)
        final=final[0]
        print(final, type(final))
        if (final == '1'):
            result = 'Lungs are healthy!!'
        elif (final == '2'):
            result = 'Lung cancer detected !!!!!!'
        # qry="INSERT INTO `uploads` VALUES(NULL,%s,%s,%s,CURDATE())"
        # val=(uid,files,result)
        # iud(qry,val)
        return jsonify({'task': 'success','res':result})
    except Exception as e:
        print(e)
        return jsonify ({'task': 'error'})



# @app.route('/viewnearestpharmacy', methods=['post'])
# def viewnearestpharmacy():
#     loginid=request.form['loginid']
#     query="select * from pharmacy where loginid=%s "
#     res=androidselectall(query,loginid)
#     return jsonify(res)
@ app.route('/purchasemedicine', methods=['post'])
def purchasemedicine():
    userid=request.form['userid']
    medicineid=request.form['medicineid']
    quantity=request.form['quantity']
    qry = "insert into booking values(%s,%s,curdate(),%s,'pending')"
    val =(userid,medicineid,quantity)
    iud(qry,val)
    return jsonify({'task': 'success'})
@app.route('/viewdepartment', methods=['post'])
def viewdepartment():
    loginid=request.form['loginid']

    qry="select * from department where loginid=%s"
    res=androidselectall(qry,loginid)
    return jsonify(res)
@ app.route('/VIEWDOC', methods=['post'])
def VIEWDOC():


    qry="select * from doctor "
    res=androidselectallnew(qry)
    return jsonify(res)
@ app.route('/addcomplaint', methods=['post'])
def addcomplaint():
    loginid=request.form['loginid']
    complaint= request.form['complaint']
    qry="insert into complaint values(null,%s,%s,curdate(),'pending'"
    val = (loginid,complaint)
    iud(qry,val)
    return jsonify({'task': 'success'})





@app.route('/viewnearestpharmacy',methods=['POST'])
def viewnearestcharity():
    print(request.form)
    latitude=request.form['lati']
    longitude=request.form['longi']
    print("_________________________________________")

    print(latitude)
    cmd.execute("SELECT `pharmacy`.*, (3959 * ACOS ( COS ( RADIANS('"+latitude+"') ) * COS( RADIANS( Lat) ) * COS( RADIANS( Lon ) - RADIANS('"+longitude+"') ) + SIN ( RADIANS('"+latitude+"') ) * SIN( RADIANS( Lat ) ))) AS user_distance FROM `pharmacy` HAVING user_distance  < 31.068")
    s=cmd.fetchall()
    print(s)
    row_headers = [x[0] for x in cmd.description]
    json_data = []
    for result in s:
        json_data.append(dict(zip(row_headers, result)))
    print(json_data)

    return jsonify(json_data)





@app.route('/viewreply', methods=['post'])
def viewreply():
    print(request.form)
    loginid=request.form['lid']

    qry= "select * from complaint where login_id=%s "
    res = androidselectall(qry, loginid)
    print(res)
    return jsonify(res)



@app.route('/viewdept', methods=['post'])
def viewdept():

    qry= "select * from department  "
    res = androidselectallnew(qry)
    return jsonify(res)

@app.route('/addfeedback', methods=['post'])
def addfeedback():
    loginid = request.form['loginid']
    feedback = request.form['feedback']

    qry = "insert into complaint values(%s,curdate())"
    val=(loginid,feedback)
    iud(qry, val)
    return jsonify({'task': 'success'})
#
#
#
#
#
#
#
#
#
#
#

@app.route('/login',methods=['post'])
def login():
    print(request.form)
    username=request.form['uname']
    password=request.form['pass']
    Cmd.execute("select * from login where username='" + username + "' and password='" + password + "'")
    S = Cmd.fetchone()
    if S is None:
        return jsonify({'task': "invalid"})
    else:
        return jsonify({'task': str(S[0]),'type':S[3]})

@app.route('/registration',methods=['get','post'])
def registration():
    fname = request.form['fnm']
    lname = request.form['lname']
    gender = request.form['gender']
    dob = request.form['dob']
    place = request.form['place']
    post = request.form['post']
    pin = request.form['pin']
    phone = request.form['phone']
    username = request.form['uname']
    password = request.form['pass']
    Cmd.execute("insert into login values(null, '" + username + "','" + password + "','user')")
    id = con.insert_id()
    Cmd.execute("insert into `user register` values(null,'"+str(id)+"','"+fname+"','"+lname+"','"+gender+"','"+dob+"','"+place+"','"+post+"','"+pin+"','"+phone+"')")
    con.commit()
    return jsonify({'task': "success"})

@app.route('/complaint',methods=['get','post'])
def complaint():
    login_id = request.form['lid']
    complaint = request.form['com']
    q=("insert into complaint values(null,%s,%s,curdate(),'pending')")
    v=login_id,complaint
    iud(q,v)
    return jsonify({'task': "success"})

@app.route('/feedback',methods=['get','post'])
def feedback():
    login_id = request.form['lid']
    feedback = request.form['feed']
    Cmd.execute("insert into feedback values(null,'" + str(login_id) + "','" + feedback + "',curdate())")
    con.commit()
    return jsonify({'task': "success"})



@app.route ('/viewp',methods=['post'])
def viewp():
    login_id = request.form['lid']
    Cmd.execute("SELECT `booking`.`booking_id`,`booking`.`date`,`booking`.`status`,`doctor`.`Fname`,`Lname` FROM `booking`  JOIN `doctor_tb` ON `booking`.`doctor_id`=`doctor`.`Login_id` WHERE `booking`.`user_id`="+login_id)
    row_headers = [x[0] for x in Cmd.description]
    results = Cmd.fetchall()
    print(login_id)
    json_data = []
    for result in results:
        json_data.append(dict(zip(row_headers, result)))
    con.commit()
    print(json_data)
    return jsonify(json_data)


# @app.route ('/viewp1',methods=['post'])
# def viewp1():
#     bid = request.form['bid']
#     Cmd.execute("SELECT * FROM `priscription_info_tb` WHERE `booking_id`="+bid)
#     row_headers = [x[0] for x in Cmd.description]
#     results = Cmd.fetchall()
#     print(bid)
#     json_data = []
#     for result in results:
#         json_data.append(dict(zip(row_headers, result)))
#     con.commit()
#     print(json_data)
#     return jsonify(json_data)

@app.route ("/bookdoc",methods=['post'])
def bookdoc():
    userid=request.form['uid']
    doctorid=request.form['did']
    q=("insert into booking values(null,%s,%s,curdate(),'pending')")
    v=doctorid,userid
    iud(q,v)
    return jsonify({'task': "success"})


@app.route ("/bkstatus",methods=['post'])
def bkstatus():
    print(request.form)
    id=request.form['lid']
    q="SELECT `doctor`.`Fname`,`doctor`.`Lname`,booking.* FROM `doctor` JOIN `booking` ON `booking`.`doctor_id`=`doctor`.`Login_id` WHERE `booking`.`user_id`=%s"
    res=androidselectall(q,id)
    return jsonify(res)
@app.route ("/viewfacility",methods=['post'])
def viewfacility():

    q="select * from facility"
    res=androidselectallnew(q)
    return jsonify(res)

# @app.route("/docpayment",methods=['post'])
# def docpayment():
#     userid=request.form['userid']
#     bankname=request.form['bname']
#     acnumber=request.form['acno']
#     ifsc=request.form['ifsc']
#     Cmd.execute("SELECT * FROM `bank_tb` WHERE `bank_name`='"+bankname+"' AND `ifsc`='"+acnumber+"' AND `account_number`='"+ifsc+"' WHERE `bank_tb`.`user_id`='"+str(userid)+"'")
#     s=Cmd.fetchone()
#     if s is None:
#         return jsonify({'task': "success"})
#     else:
#         return jsonify({'task': "failed"})
#




# @app.route('/viewpriscription')
# def viewpriscription():
#     id = request.form['id']
#     user_id = request.form['uid']
#     priscription = request.form['pris']
#     date = request.form['reqfrm']
#     Cmd.execute("select * from priscription_tb where id ='"+str(id)+"','"+user_id+"','"+priscription+"','"+date+"' ")
#     con.commit()

# @app.route('/viewdocmin',methods=['post'])
# def viewdocmin():
#     print("okkkkk")
#     print(request.form)
#     syml=request.form['sl']
#     res=selection(syml).split('#')
#     json_data = []
#     for r in res:
#         Cmd.execute("SELECT `doctor_tb`.*,`hospital_tb`.`name`,`hospital_tb`.`place` FROM `doctor_tb` JOIN `hospital_tb` ON `hospital_tb`.`lid`=`doctor_tb`.`hos_id` WHERE `doctor_tb`.`departmnt`='"+r+"'")
#         row_headers = [x[0] for x in Cmd.description]
#         results = Cmd.fetchall()
#
#
#         for result in results:
#             json_data.append(dict(zip(row_headers, result)))
#     con.commit()
#     print(json_data)
#     return jsonify(json_data)
#

# @app.route('/viewshedule',methods=['post'])
# def viewshedule():
#
#     syml=request.form['did']
#     print(syml,"sssss")
#     json_data=[]
#     Cmd.execute("SELECT * FROM `schedule_tb` WHERE `date`>=CURDATE() AND `doc_id`='"+syml+"'")
#     row_headers = [x[0] for x in Cmd.description]
#     results = Cmd.fetchall()
#
#
#     for result in results:
#         json_data.append(dict(zip(row_headers, result)))
#     con.commit()
#     print(json_data)
#     return jsonify(json_data)





if __name__=='__main__':
    app.run(host='0.0.0.0',port=5000)


