importPackage(org.kona.js);
importPackage(java.util);
importPackage(java.text);

model = kona.model('user');

function post(req){
    
    var user = kona.obj();
    user.put("name",req.get("user"));
    var result = model.query(user);
    
    if (result==null || result.size()==0)
        return kona.obj(false);
    
    var user = result.get(0);
    
    if (user.get("pass")==req.get("pass"))
        return kona.obj(true);
    else
        return kona.obj(false);
}

function test(){
    req = kona.obj();
    req.put("user","q");
    req.put("pass","1");
    return post(req);
}