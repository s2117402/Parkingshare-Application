import jpush as jpush
from conf import app_key, master_secret
def sendinfo(username,parkingmame):
    _jpush = jpush.JPush(app_key, master_secret)
    _jpush.set_logging("DEBUG")
    push = _jpush.create_push()

    push.audience = jpush.audience(
        jpush.alias(str(username))
    )
    info="Dear "+username+",you car has parked at "+parkingmame+"!"
    push.notification = jpush.notification(alert=str(info))
    push.platform = jpush.all_
    print (push.payload)
    push.send()