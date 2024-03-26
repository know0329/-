import pandas as pd
import fasttext
from PyKomoran import *

data = pd.read_csv("Data.csv",sep=",")
komoran = Komoran("EXP")
komoran.set_user_dic("dic.user")

df = pd.DataFrame(columns=['category', 'document'])
df['category'] = '__label__' + data['category']
print(data.columns)
df['document'] = data['content'].apply(lambda x: komoran.nouns(x))
df['document'] = df['document'].apply(lambda x: " ".join(x))
df['document'] = " "+df['document']
print(df)
df.to_csv('labeldata.txt', sep=",", index=False)
labeling = pd.read_csv("labeldata.txt", sep=",")
model = fasttext.train_supervised("labeldata.txt",epoch=25)
print(model.predict("PC방"))
model.save_model("fasttext.bin")
