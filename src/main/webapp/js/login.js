/**
 * Created by fixopen on 13/4/15.
 */

window.addEventListener('load', function (e) {
    var doc = document
   /* var rememberPassword = doc.getElementById('rememberPassword')
    rememberPassword.addEventListener('click', function(e) {
        if (e.target.checked) {
            //remember to local storage
        } else {
            //clear from local storage
        }
    }, false)*/
    var login = doc.getElementById('login')
    login.addEventListener('click', function(e) {
        var select = $('#select option:selected') .val();
        if(select != "no"){
            var name = doc.getElementById('name')
            var password = doc.getElementById('password')
            g.postData('/api/sessions', [
                {name: 'Content-Type', value: 'application/json'},
                {name: 'Accept', value: 'application/json'}
            ], {subjectType:"Personal",loginName:name.value.trim(),password: password.value.trim()}, function(r) {
                if (r.meta.code == 200) {
                    //ok
                    //store sessionId
//                    g.setCookie('sessionId', r.data.sessionId)
                    location.href = 'index.jsp?name=admin'
                } else {
                    //error
                }
            })
        }else{
            alert("请选择用户类型")
        }

    }, false)
}, false)
