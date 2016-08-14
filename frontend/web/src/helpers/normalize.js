/**
 * Created by gwonseongbong on 8/3/16.
 */

export const normalizeYear = (value, previousValue) => {
    if (!value){
        return value;
    }
    const onlyNums = String(value).replace(/[^\d]/g, '');
    if(onlyNums>9999){
        return previousValue;
    }
    return onlyNums;
}

export const normalizeSubject = (value, previousValue) => {
    if (!value){
        return value;
    }
    if(String(value).length > 100){
        return previousValue;
    }
    return value;
}

