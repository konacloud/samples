importPackage(org.kona.js);

model = kona.model('user');

function get(){
    return model.all();
}

function post(req){
    
    var user = kona.obj();
    user.put("name",req.get("name"));
    var result = model.query(user);
    
    var response;
    if (result==null || result.size()==0){
        //no existe el usuario con ese nick
        model.insert(req);
        response = kona.obj(true);
    }else{
        response = kona.obj(false);
        response.put("msg","usuario ya existente");
    }
    return response;
}

function test(){    
    var obj = kona.obj();
    obj.put("name","taio");
    obj.put("email","taio");
    obj.put("pass","taio");    
    return post(obj);
}