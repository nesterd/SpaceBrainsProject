#!/usr/bin/env python3
import os
import time
import smtplib
import sys


def send_mail(email, name, username, password):
    password_file = os.path.join(os.path.dirname(__file__), 'crawler.google')
    with open(password_file, 'r') as passwd:
        smtp = smtplib.SMTP('smtp.googlemail.com', 25)
        crwl_login = 'crawler@spacebrains.bblde3hap.info'
        crwl_pass = passwd.readline()
        from_addr = 'Spacebrains crawler <{0}>'.format(crwl_login)
        to_addr = '{0} <{1}>'.format(name, email)
        subj = 'Your new password to Spacebrains crawler system!'
        date = time.strftime('%d-%m-%Y %H:%M', time.localtime())
        message_text = '''
        Hello, dear {0}!
        We've got a request to reset your password.

        From now your new login credentials will be:
        username: {1}
        password: {2}

        With kind regards,
        Spacebrains team.
        '''.format(name, username, password)

        smtp.set_debuglevel(0)
        smtp.ehlo()
        smtp.starttls()
        smtp.ehlo()
        smtp.login(crwl_login, crwl_pass)
        msg = 'From: {0}\nTo: {1}\nSubject: {2}\nDate: {3}\n\n{4}'.format(
            from_addr,
            to_addr,
            subj,
            date,
            message_text
        ).encode('utf-8')
        smtp.sendmail(from_addr, to_addr, msg)
        smtp.quit()

if __name__ == '__main__':
    send_mail(sys.argv[1], sys.argv[2], sys.argv[3], sys.argv[4])
