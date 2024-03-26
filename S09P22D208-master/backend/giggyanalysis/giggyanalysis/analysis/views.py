from django.shortcuts import render
import pandas as pd
import json
from django.http import JsonResponse
import fasttext
from django.views.decorators.csrf import csrf_exempt
from PyKomoran import *
import requests

# Create your views here.
def preprocessing(data):
    json_data = pd.json_normalize(data)
    # json_data["content"] = 
    return analysis(json_data)
def analysis(data):
    komoran = Komoran("EXP")
    komoran.set_user_dic("user_dic/dic.user")
    model = fasttext.load_model("fasttext_model/fasttext.bin")
    data["preprocess_content"] = data["content"].apply(lambda x: komoran.nouns(x))
    data["preprocess_content"] = " "+data["preprocess_content"].apply(lambda x: " ".join(x))
    data["category"] = data["preprocess_content"].apply(lambda x: model.predict(x)[0][0].replace("__label__",""))
    data["category"] = data["category"].apply(lambda x: x.replace(",",""))
    data.loc[data["deposit"].gt(0), "category"] = "기타"
    data = data.drop("preprocess_content", axis=1)
    analysis_data = data.to_dict(orient='records')
    return analysis_data
    
@csrf_exempt
def receive(request):
    if request.method == 'POST':
        try:
            request_data = json.loads(request.body.decode('utf-8'))
            # JSON 데이터를 파이썬 객체로 파싱
        except json.JSONDecodeError:
            return JsonResponse({'error': 'Invalid JSON in request body'}, status=400)
        url = "https://j9d208.p.ssafy.io:8282/api/v1/bank/search-transaction"
        data = {
                'accountNumber' : request_data['accountNumber'],
                'startDate' : request_data['startDate'],
                'endDate' : request_data['endDate']    
            }
        print(data)
        headers = {'Content-Type': 'application/json'}
        response = requests.post(url, data=json.dumps(data), headers=headers)
        print(response)
        if response.status_code == 200:
            history_data = response.json()
            analysis_data = preprocessing(history_data)
            print(analysis_data)
            return JsonResponse({"status" : "success", "data" : analysis_data})
        else:
            print(f"요청 실패: {response.status_code}")
    
    else:
        return JsonResponse({"status" : "fail", "error" : "Only Post"})