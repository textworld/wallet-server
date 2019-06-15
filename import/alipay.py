import requests
import json


class Recorder:

    def __init__(self):
        self.domain = "http://192.168.2.8:5000"
        self.header = {'Content-Type': 'application/json'}

    def alipay(self, data_map):
        trade_time = data_map["付款时间"]
        if len(trade_time) == 0:
            trade_time = data_map["交易创建时间"]

        trade_time = trade_time.replace(" ", "T")

        if data_map["收/支"] == "收入":
            trade_type = 1
        else:
            trade_type = 2


        json_map = {
            "money": data_map["金额（元）"],
            "source": "alipay",
            "date": trade_time,
            "order_id": "aplipay-" + data_map["交易号"],
            "trade_type": trade_type,
            "name": data_map["商品名称"],
            "trade_status": data_map["交易状态"],
            "status": 0,
        }
        json_str = json.dumps(json_map)
        response = requests.post(self.domain.strip("/") + "/api/v1/record", data=json_str, headers=self.header)
        return response


class DataFieldError(ValueError):
    def __init__(self, arg):
        self.args = arg


def line_to_list(line, ch):
    line = line.strip().rstrip(ch)
    fields = line.split(ch)
    ret = []
    for field in fields:
        ret.append(field.strip())
    return ret


def package_map(fields, data):
    if len(fields) != len(data):
        raise DataFieldError("fields have not equal number as data")

    ret_map = {}

    for index in range(len(fields)):
        if len(fields[index]) == 0:
            raise DataFieldError("field name at index " + str(index) + " is empty")
        ret_map[fields[index]] = data[index]

    return ret_map


if __name__ == "__main__":
    with open("/Users/textworld/Downloads/alipay_record_20190615_1657_1.csv", encoding="gb18030") as f:
        for i in range(4):
            f.readline()
        title = f.readline()

        title_list = line_to_list(title, ",")

        recorder = Recorder()
        data_line = f.readline()
        line_n = 0
        while data_line:
            line_n += 1
            data_row = line_to_list(data_line, ",")
            if len(data_row) != len(title_list):
                break
            row_map = package_map(title_list, data_row)
            print(row_map)
            recorder.alipay(row_map)
            data_line = f.readline()
