from flask import *
from  src.dbconnector import *
app=Flask(__name__)
app.secret_key="aaa"
@app.route('/')

def main():
    return render_template('login.html')
import functools
def login_required(func):
    @functools.wraps(func)
    def secure_function():
        if "lid" not in session:
            return redirect ("/")
        return func()
    return secure_function



@app.route("/logout")
@login_required


def logout():
    session.clear()
    return render_template("login.html")
@app.route('/log',methods = ['post'])
@login_required
def log():
    uname = request.form['textfield']
    password = request.form['textfield3']
    query="select * from login where username=%s and password= %s"
    val = (uname, password)
    res=selectone(query,val)
    if res[3] == 'admin':
        session['lid'] = res[0]
        return'''<script> alert ("login success");window.location="/admin_home"</script>'''
    elif res[3]=='doctor':
        session['lid']=res[0]
        return'''<script> alert ("login success");window.location="/doctormodule"</script>'''
    elif res[3] == 'hospital':
        session['lid']=res[0]
        return '''<script> alert ("login success");window.location="/hospitalmodule"</script>'''
    elif res[3] == 'pharmacy':
        session['lid'] = res[0]
        return'''<script> alert ("login success");window.location="/pharmacymodule"</script>'''

@app.route('/admin_home')
@login_required
def admin_home():
    return render_template('Admin home page.html')
@app.route('/addhospital1',methods=['post'])
@login_required
def addhospital1():
      name = request. form['textfield']
      place= request.form['textfield2']
      email = request. form['textfield6']
      phone = request. form['textfield7']
      uname = request .form['textfield8']
      pswd = request. form['textfield9']
      post = request. form['textfield3']
      pin = request.form['textfield4']
      query="insert into login values(null,%s,%s,'hospital')"
      val = (uname,pswd)
      id = iud(query,val)
      query = "insert into hospital values(null,%s,%s,%s,%s,%s,%s,%s)"
      val = (str(id),name,place,email,phone,post,pin)
      iud(query,val)
      return '''<script>alert("insert"); window. location = '/viewhospital' </script>'''
@app.route('/adddoctor1',methods=['post'])
@login_required
def adddoctor1():
      fname = request.form['textfield4']
      lname = request.form['textfield22']
      dob   = request.form['textfield32']
      gender = request.form['radiobutton']
      qualification = request.form['textfield52']
      specification = request.form['textfield62']
      place = request.form['textfield82']
      post = request.form['textfield92']
      pin = request.form['textfield112']
      email = request.form['textfield122']
      phone = request.form['textfield122']
      Username = request.form['textfield123']
      password = request.form['textfield124']
      query1 ="insert into login values(null,%s,%s,'doctor')"
      value1 =(Username,password)
      id =iud(query1,value1)
      query ="insert into doctor values(null,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)"
      value2=(str(id),fname,lname,dob,gender,qualification,specification,place,post,pin,email,phone,str(session['lid']))
      iud(query,value2)
      return'''<script>alert("inserted"); window.location='/managedoctor'</script>'''
@app.route('/addpharmacy1',methods=['post'])
@login_required
def addpharmacy1():
    name = request.form['textfield']
    place = request.form['textfield2']
    post = request.form['textfield3']
    pin = request.form['textfield4']
    email = request.form['textfield5']
    phone = request.form['textfield6']
    Latitude = request.form['textfield9']
    longitude = request.form['textfield10']
    username = request.form['textfield7']
    password = request.form['textfield8']
    query="insert into login values (null,%s,%s,'pending')"
    value2=(username,password)
    id =iud(query,value2)
    query="insert into pharmacy values(NULL,%s,%s,%s,%s,%s,%s,%s,%s,%s)"
    value2=(str(id),name,place,post,pin,email,phone,Latitude,longitude)
    print(value2)
    iud(query,value2)
    return'''<script>alert("inserted"); window.location='/'</script>'''


@app.route('/addcomplaints')
@login_required
def addcomplaints():
    return render_template('Add complaint.html')
@app.route('/addcomplaints1',methods=['post'])
@login_required
def addcomplaints1():
    complaint=request.form["textfield"]
    qry="insert into complaint values(null,%s,%s,curdate(),'pending')"
    val=(session['lid'],complaint)
    iud(qry,val)
    return'''<script>alert("insert"); window.location = '/doctormodule' </script>'''

@app.route('/viewhospital')
@login_required
def viewhospital():
    query="select * from hospital"
    res = select(query)
    return render_template('view hospital.html',val=res)

@app.route('/addfeedback')
@login_required
def addfeedback():
    return render_template('add feedback.html')
@app.route('/addfeedback1',methods=['post'])
@login_required
def addfeedback1():
    feedback =request.form["textfield"]
    qry="insert into feedback values(null,%s,%s,curdate())"
    val=(session['lid'],feedback)
    iud(qry,val)
    return'''<script>alert("insert"); window.location ='/doctormodule' </script>'''


@app.route('/viewfacility')
@login_required
def viewfacility():
    return render_template('viewfacitily.html')



@app.route('/addmedicine')
@login_required
def addmedicine():
      return render_template('add medicine.html ')

@app.route('/addmedicine1',methods=['post'])
@login_required
def addmedicine1():
    medicinename=request.form['textfield']
    description=request.form['textfield2']
    price=request.form['textfield4']
    # photo=request.files['file']
    # file=secure_filename(photo.filename)
    # photo.save(os.path.join("static/images",file))
    expirydate=request.form['textfield5']
    dosage=request.form['textfield6']
    qry="insert into medicine values(null,%s,%s,%s,%s,%s,%s)"
    val=(medicinename,description,price,expirydate,dosage,(session['lid']))
    print(val)
    iud(qry,val)
    return '''<script>alert("insert"); window.location ='/addlocation' </script>'''


@app.route('/addnotification',methods=['post'])
@login_required
def addnotification():
    return render_template('add notification.html')

@app.route('/addnotification1',methods=['post'])
@login_required
def addnotification1():
    notification=request.form['textfield']
    query="insert into notifiication  values(null,%s,curdate())"
    val=(notification)
    iud(query,val)
    return '''<script>alert("inserted"); window.location='/viewnotification' </script>'''

@app.route('/adddoctor',methods=['post'])
@login_required
def adddoctor():
    return render_template('add doctor.html ')

@app.route('/viewnotification')
@login_required
def viewnotification():
    query="select * from notifiication"
    res=select(query)
    print(res)
    return render_template('view notification.html' ,val=res)


@app.route('/Adminhomepage')
@login_required
def Adminhomepage():
    return render_template('Adminhomepage')

@app.route('/booking')
@login_required
def booking():
    return render_template('booking')


@app.route('/bookingstatus')
@login_required
def bookingstatus():
    return render_template('booking status.html')


@app.route('/doctormodule')
@login_required
def doctormodule():
    return render_template('doctor module.html')

@app.route('/edithospital')
@login_required
def edithospital():
    id =request.args.get('id')
    session['id']=id
    query="select * from hospital where Login_id=%s"
    val=str(id)
    res=selectone(query,val)
    return render_template('hospital edit.html',val=res)
@app.route('/updatehospital',methods=['post'])
@login_required
def updatehospital():
    name = request.form['textfield']
    place = request.form['textfield2']
    email = request.form['textfield6']
    phone = request.form['textfield7']
    id=session['id']
    post = request.form['textfield3']
    pin = request.form['textfield4']
    query="update hospital set name =%s,place=%s,email=%s,phone=%s,post=%s,pin=%s where Login_id=%s"
    val=(name,place,email,phone,post,pin,str(id))
    iud(query,val)
    return '''<script>alert("updated"); window.location='/viewhospital' </script>'''

@app.route('/editstaff')
@login_required
def editstaff():
    return render_template('edit hospital.html')

@app.route('/feedback')
@login_required
def feedback():
    return render_template('feed back.html')
@app.route('/hospitalmodule')
@login_required
def hospitalmodule():
    return render_template('hospital module.html')
@app.route('/addhospital',methods=['post'])
@login_required
def addhospital():
    return render_template('add hospital.html')


@app.route('/managecomplaint')
@login_required
def managecomplaint():
    query="SELECT `user register`.`Fname`,`user register`.`Lname`,`complaint`.`Complaint`,`complaint`.`date`,complaint.Complaint_id FROM `user register` JOIN  `complaint`  ON`complaint`.`login_id`=`user register`.`Login_id`"
    res=select(query)
    return render_template('Manage complaint.html',val=res)
@app.route('/managedoctor')
@login_required
def managedoctor():
    query="select * from doctor"
    res=select(query)
    print(res)
    return render_template('Manage doctor.html',val=res)
@app.route('/doctoredit')
@login_required
def doctoredit():
    id =request.args.get('id')
    session['id']=id
    query="select * from doctor where Login_id=%s"
    val=str(id)
    res=selectone(query,val)
    print(res)
    return render_template('Edit doctor.html',val=res)
@app.route('/adddepartment',methods=['post'])
@login_required
def adddepartment():
    return render_template('add departmenti.html')

@app.route('/adddepartment2',methods=['post'])
@login_required
def adddepartment2():
    departmentname=request.form['textfield']
    description=request.form['textfield2']
    qry="INSERT INTO department VALUES(NULL,%s,%s,%s)"
    value=(session['lid'],departmentname,description)
    iud(qry,value)
    return '''<script>alert("inserted"); window.location='/viewdepartment'</script>'''

# @app.route('/edit')
# def adddepartment1():
#
#      print(res)
#      return

@app.route('/updatedepartment', methods=['post'])
@login_required
def updatedepartment():
    name = request.form['textfield']
    description = request.form['textfield2']
    qry="UPDATE department SET NAME=%s,description=%s WHERE Department_id=%s "
    val=(name,description,session['did'])
    iud(qry, val)
    return '''<script>alert("updated"); window.location='/viewdepartment' </script>'''
@app.route('/deletedepartment')
@login_required
def deletedepartment():
    id=request.args.get('id')
    query="delete from department where department_id=%s"
    iud(query,id)
    return '''<script>alert("deleted"); window.location='/viewdepartment'</script>'''



@app.route('/updatedoctor',methods=['post'])
@login_required
def updatedoctor():
    fname = request.form['textfield']
    lname = request.form['textfield2']
    dob = request.form['textfield3']
    gender = request.form['radiobutton']
    qualification = request.form['textfield5']
    specification = request.form['textfield6']
    place = request.form['textfield7']
    post = request.form['textfield8']
    pin = request.form['textfield9']
    email = request.form['textfield11']
    phone = request.form['textfield12']
    id=session['id']

    query="update doctor set fname =%s,lname=%s,dob=%s,gender=%s,qualification=%s,specification=%s,place=%s,post=%s,pin=%s,email=%s,phone=%s where Login_id=%s"
    val=(fname,lname,dob,gender,qualification,specification,place,post,pin,email,phone,str(id))
    iud(query,val)
    return '''<script>alert("updated"); window.location='/managedoctor' </script>'''



@app.route('/deletedoctor')
@login_required
def deletedoctor():
    id=request.args.get('id')
    query="delete from doctor where Login_id=%s"
    val=str(id)
    iud(query,val)
    query2="delete from login where login_id=%s"
    val2=str(id)
    print(val2)
    iud(query2,val2)
    return '''<script>alert("deleted"); window.location='/managedoctor'</script>'''





@app.route('/pharmacy')
@login_required
def pharmacy():
    return render_template(' pharmacy.html')
@app.route('/pharmacymodule')
@login_required
def pharnmacymodule():
    return render_template('pharmacy moduler.html')
@app.route('/pharmacyregister')
@login_required
def pharmacyregister():
    return render_template('pharmacy registeer.html')


@app.route('/viewbooking')
@login_required
def viewbooking():
    qry="SELECT `booking`.*,`user register`.`Fname`,`user register`.`Lname`,`doctor`.`Fname`,`doctor`.`Lname` FROM `booking` JOIN `user register` ON `booking`.`user_id`=`user register`.`Login_id` JOIN `doctor` ON `doctor`.`Login_id`=`booking`.`doctor_id` WHERE `booking`.`booking_status`='pending'"
    res=select(qry)
    return render_template('view booking.html',val=res)

@app.route('/viewbooking2')
@login_required
def viewbooking2():
    query="SELECT `user register`.`Fname`,`user register`.`Lname`,`booking`.* FROM`booking`JOIN`user register`ON `booking`.`user_id`=`user register`.`Login_id`where booking.booking_status='pending'"
    res=select(query)
    return render_template('view booking2.html',val=res)
@app.route('/acceptbooking2')
@login_required
def acceptbooking2():
    id = request.args.get('id')
    query = "update booking set booking_status='accept' where booking_id=%s"
    iud(query, id)
    return '''<script>alert("accepted");window.location='/viewbooking2'</script>'''


@app.route('/rejectbooking2')
@login_required
def rejectbooking2():
    id=request.args.get('id')
    query="update booking set booking_status='reject' where booking_id=%s"
    iud(query,id)
    return '''<script>alert("rejected");window.location='/viewbooking2'</script>'''


@app.route('/acceptbooking')
@login_required
def acceptbooking():
    id = request.args.get('id')
    query = "update booking set booking_status='accept' where booking_id=%s"
    iud(query, id)
    return '''<script>alert("accepted");window.location='/viewbooking'</script>'''

    return
@app.route('/rejectbooking')
@login_required
def rejectbooking():
    id=request.args.get('id')
    query="update booking set booking_status='reject' where booking_id=%s"
    iud(query,id)
    return '''<script>alert("rejected");window.location='/viewbooking'</script>'''


@app.route('/deletehospital')
@login_required
def deletehospital():
    id=request.args.get('id')
    query="delete  from hospital where Login_id=%s"
    val=str(id)
    iud(query,val)
    return '''<script>alert("deleted"); window.location='/viewhospital' </script>'''
@app.route('/deletenotification')
@login_required
def deletenotification():
    id=request.args.get('id')
    query="delete  from notifiication  where Notification_id=%s"
    val=str(id)
    iud(query,val)
    return '''<script>alert("deleted"); window.location='/viewnotification ' </script>'''



@app.route('/viewdepartment')
@login_required
def viewdepartment():
    qry="SELECT * FROM department"
    res=select(qry)
    return render_template('veiwdepartment.html',val=res)

@app.route('/editdepartment')
@login_required
def editdepartment():
    id = request.args.get('id')
    session['did'] = id
    qry = "select * from department where Department_id=%s"
    res = selectone(qry, session['did'])
    return render_template('edit department.html',val=res)


@app.route('/editdoctor')
@login_required
def editdoctor():
    return render_template('Edit doctor.html')

@app.route('/addfacility',methods=['post'])
@login_required
def addfacility():
    return render_template('add facility.html')

@app.route('/addfacility1',methods=['post'])
@login_required
def addfacility1():
    facility=request.form["textfield2"]
    description=request.form["textfield"]
    query="insert into facility values(null,%s,%s,%s)"
    val=(str(session['lid']),facility,description)
    print(val)
    iud(query,val)
    return redirect('/viewfacility')



@app.route('/reply')
@login_required
def reply():
    id = request.args.get('id')
    session['cid']=id
    return render_template('Reply.html')
@app.route('/replyy',methods=['post'])
@login_required
def replyy():
    id=session['cid']
    reply= request.form['textfield']
    qry="UPDATE `complaint` SET `Reply`=%s WHERE `Complaint_id`=%s"
    val=(reply,str(id))
    iud(qry,val)
    return '''<script>alert("sended"); window.location='/managecomplaint'</script>'''

@app.route('/viewreply')
@login_required
def viewreply():
    qry="SELECT * FROM `complaint` WHERE `login_id`=%s"
    val=session['lid']
    res=selectall(qry,val)
    return render_template('veiwreply.html',val=res)


@app.route('/addstaff')
@login_required
def addstaff():
    return render_template('add staff.html')


@app.route('/viewfeedback')
@login_required
def viewfeedback():
    qry="SELECT `user register`.`Fname`,`user register`.`Lname`,`feedback`.`Feedback`,`feedback`.`Date` FROM `user register` JOIN`feedback`ON`feedback`.`Login_id`=`user register`.`Login_id`"
    res=select (qry)
    return render_template('view feedback.html',val=res)


@app.route('/approvepharmacy')
@login_required
def approvepharmacy():
    query="select * from pharmacy where `Login_id` IN ( SELECT `login`.`login_id` FROM `login` WHERE `usertype`='pending')"
    res=select(query)
    return render_template('Approve pharmacy.html',val=res)

@app.route('/approvepharmacy1')
@login_required
def approvepharmacy1():
    id = request.args.get('id')
    query = "UPDATE login SET usertype='pharmacy' WHERE login_id=%s"
    val = str(id)
    iud(query,val)
    return '''<script>alert("updated"); window.location='/approvepharmacy' </script>'''


@app.route('/rejectpharmacy')
@login_required
def rejectpharmacy():
    id = request.args.get('id')
    query = "update login SET usertype='reject' WHERE login_id=%s"
    val = str(id)
    iud(query, val)
    return '''<script>alert("rejected"); window.location='/approvepharmacy' </script>'''


@app.route('/viewprofile')
@login_required
def viewprofile():
    print(session['lid'],"====================")
    qry="SELECT * FROM `doctor` WHERE `Login_id`=%s"
    res=selectone(qry,session['lid'])
    print(res)
    return render_template('view proffile.html',val=res)
@app.route('/updateprofile',methods=['post'])
@login_required
def updateprofile():
    fname = request.form['textfield']
    lname = request.form['textfield']
    place = request.form['textfield2']
    post = request.form['textfield3']
    pin = request.form['textfield4']
    email = request.form['textfield5']
    phone = request.form['textfield6']
    qry="update doctor set fname=%s,lname=%s,place=%s,post=%s,pin=%s,email=%s,phone=%s where login_id=%s"
    val=(fname,lname,place,post,pin,email,phone,session['lid'])
    iud(qry,val)
    return '''<script>alert("updated"); window.location='/doctormodule' </script>'''



@app.route('/viewphprofile')
@login_required
def viewphprofile():
    print(session['lid'],"====================")
    qry="SELECT * FROM `pharmacy` WHERE `Login_id`=%s"
    res=selectone(qry,session['lid'])
    print(res)
    return render_template('view ph proffile.html',val=res)
@app.route('/updatpheprofile',methods=['post'])
@login_required
def updatpheprofile():
    fname = request.form['textfield']

    place = request.form['textfield2']
    post = request.form['textfield3']
    pin = request.form['textfield4']
    email = request.form['textfield5']
    phone = request.form['textfield6']
    qry="UPDATE `pharmacy` SET `Name`=%s,`Place`=%s,`Post`=%s,`Pin`=%s,`E-mail`=%s,`Phone`=%s WHERE `Login_id`=%s"
    val=(fname,place,post,pin,email,phone,session['lid'])
    iud(qry,val)
    return '''<script>alert("updated"); window.location='/pharmacymodule' </script>'''

@app.route('/addlocation')
@login_required
def addlocation():
    return render_template('add location.html')
@app.route('/addlocation1',methods=['post'])
@login_required
def addlocation1():
    latitude=request.form["textfield"]
    longitude=request.form["textfield2"]
    qry="insert into location values(null,%s,%s,%s)"
    val=(str(session['lid']),latitude,longitude)
    print(val)
    iud(qry,val)
    return '''<script>alert("inserted"); window.location='/pharmacymodule' </script>'''

@app.route('/viewmedicineorder')
@login_required
def viewmedicineorder():
    qry="SELECT `orders`. * ,`medicine`.`medicine_name`,`user register`.`Fname`,`user register`.`Lname` FROM `orders` JOIN `medicine` ON `orders`.`Med_id`=`medicine`.`medicine_id` JOIN `user register` ON `orders`.`User_id`=`user register`.`Login_id` WHERE STATUS='pending'"
    res=select(qry)
    return render_template('view medicine order.html',val=res)

@app.route('/acceptmedicineorder')
@login_required
def acceptmedicineorder():
     id=request.args.get('id')
     qry="update orders set status='accept' where order_id=%s"
     iud(qry,id)
     return'''<script>alert("accepted"); window.location='/pharmacymodule' </script>'''


@app.route('/rejectmedicineorder')
@login_required
def rejectmedicineorder():
    id = request.args.get('id')
    qry = "update orders set status='reject' where order_id=%s"
    iud(qry, id)
    return '''<script>alert("rejected"); window.location='/pharmacymodule' </script>'''


@app.route('/viewmedicine')
@login_required
def viewmedicine():
    return render_template('view meddicine.html')

app.run(debug=True)