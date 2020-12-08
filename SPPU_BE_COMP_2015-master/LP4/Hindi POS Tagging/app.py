from flask import Flask, render_template, flash, request
from nltk.tag import tnt
from nltk.corpus import indian
import nltk
import pandas as pd
import numpy as np
 
# App config.
DEBUG = True
app = Flask(__name__)
app.config.from_object(__name__)
app.config['SECRET_KEY'] = '7d441f27d441f27567d441f2b6176a'
 
def hindi_model():
    train_data = indian.tagged_sents('hindi.pos')
    tnt_pos_tagger = tnt.TnT()
    tnt_pos_tagger.train(train_data)
    return tnt_pos_tagger
   
model = hindi_model()
    
@app.route("/", methods=['GET', 'POST'])
def hello():
    return render_template('index.html')
 
@app.route('/tag',methods = ['POST', 'GET'])
def result():
    if request.method == 'POST':
        result = request.form
        sentence=result['sentence']
        taggedOutput=array=np.array((model.tag(nltk.word_tokenize(sentence))))
        print("Sentence : ", sentence)
        print("Tagged Output", taggedOutput)
        output={
            "input":sentence,
            "taggedOutput":taggedOutput
        }
        return render_template("result.html",output=output )


if __name__ == "__main__":
    app.run()
